package info.marcello.desalesjr.cryptonline.rsa;

import static info.marcello.desalesjr.cryptonline.rsa.Rsa.DECIMAL_FORMATTER;
import static info.marcello.desalesjr.cryptonline.rsa.Rsa.LOG_ARROW;

import java.util.ArrayList;
import java.util.List;

/**
 * The Rsa Public Key implements the public key of the RSA algorithm. It contains the public keys
 * N and E.
 * 
 * This is a thread-safe immutable class.
 * 
 * @author Marcello de Sales (marcello.desales@gmail.com)
 */
public final class RSAPublicKey {

    /**
     * The given factors
     */
    private final RSAFactors factors;
    /**
     * The FI factor
     */
    private final long fi;
    /**
     * The E factor for the public Key
     */
    private final long e;
    /**
     * The N factor for the public Key
     */
    private final long n;
    /**
     * The private log for the calculator
     */
    private final List<String> log = new ArrayList<String>();

    /**
     * @param factors are the prime numbers as the main factors of the RSA Public Key
     */
    private RSAPublicKey(RSAFactors factors) {
        this.factors = factors;

        // What was observed was that when the prime numbers are small, the reverse function fails... The new primes
        // must be big enough to make N a big number.
        this.n = this.factors.getP() * this.factors.getQ();
        this.log.add(LOG_ARROW + "Calculating public keys");
        this.log.add("          N = P * Q; N = " + DECIMAL_FORMATTER.format(this.n));

        // FI = (P-1) * (Q-1);
        this.fi = Algebra.SINGLETON.getEuler(this.factors.getP(), this.factors.getQ());
        this.e = this.generateKeyE();
        this.log.add("          E = " + DECIMAL_FORMATTER.format(this.e));
        this.log.add("");

        this.log.add("       Public Key (N,E) = (" + DECIMAL_FORMATTER.format(this.n) + " , "
                + DECIMAL_FORMATTER.format(this.e) + ")");
        this.log.add("");
    }

    /**
     * @param factors is the instance of the Factors containing the prime numbers
     * @return new RSA Public Key
     */
    public static RSAPublicKey newInstance(RSAFactors factors) {
        return new RSAPublicKey(factors);
    }

    /**
     * @return the public key E based on FI
     */
    private long generateKeyE() {

        this.log.add(LOG_ARROW + "FI = (P-1) * (Q-1); FI = " + DECIMAL_FORMATTER.format(this.fi));
        this.log.add("");
        this.log.add(LOG_ARROW + "Calculating (E)");

        long aux = 2;
        long mdc = Algebra.SINGLETON.getMDC(aux, this.fi);
        this.log.add("           While MCD(n >= 2 , " + DECIMAL_FORMATTER.format(fi) + ") != 1");
        while (mdc != 1) {

            this.log.add("           MCD(" + DECIMAL_FORMATTER.format(aux) + " , " + DECIMAL_FORMATTER.format(fi)
                    + ") = " + DECIMAL_FORMATTER.format(mdc));
            aux++;
            mdc = Algebra.SINGLETON.getMDC(aux, this.fi);
        }
        this.log.add("           MCD(" + DECIMAL_FORMATTER.format(aux) + " , " + DECIMAL_FORMATTER.format(fi) + ") = "
                + DECIMAL_FORMATTER.format(mdc) + " Correct!");
        return aux;
    }

    /**
     * @return a generated RSAPublicKey with random keys
     */
    public static RSAPublicKey newInstance() {
        return new RSAPublicKey(RSAFactors.newInstance());
    }

    public long getPrimeNumberP() {
        return this.factors.getP();
    }

    public long getPrimeNumberQ() {
        return this.factors.getQ();
    }

    public long getKeyE() {
        return this.e;
    }

    public long getKeyN() {
        return this.n;
    }
    
    public double getFactorFI() {
        return this.fi;
    }
}

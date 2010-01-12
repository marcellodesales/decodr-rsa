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
public final class RsaPublicKey {

    /**
     * The given factors
     */
    private final RsaFactors factors;
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
    private RsaPublicKey(RsaFactors factors) {
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
     * Constructs a new RSAPublicKey with pre-defined keys.
     * @param keyN is the public key N
     * @param keyE is the public key E
     */
    private RsaPublicKey(long keyN, long keyE) {
        this.e = keyE;
        this.n = keyN;
        this.fi = 0;
        this.factors = null;
    }

    /**
     * Factory method used on the RSAFactors instance, which include the prime numbers P and Q. Constructing the object
     * using this method gives access to all the utility variables and execution log.
     * @param factors is the instance of the Factors containing the prime numbers
     * @return a new instance of the RSA Public Key
     */
    public static RsaPublicKey newInstance(RsaFactors factors) {
        return new RsaPublicKey(factors);
    }

    /**
     * Factory method based on the key N and the key E. Constructing the object with this constructor DOES NOT produce
     * the execution logs and other utility variables.
     * @param keyN
     * @param keyE
     * @return a new instance of RSAPublic Key based on the given keys.
     */
    public static RsaPublicKey newInstance(long keyN, long keyE) {
        return new RsaPublicKey(keyN, keyE);
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
    public static RsaPublicKey newInstance() {
        return new RsaPublicKey(RsaFactors.newInstance());
    }

    /**
     * @return the prime number 'P' used a factor for calculating the public key.
     * @throws IllegalStateException in case the public key object was constructed with the values for N and E, without
     * passing over the generation process.
     */
    public long getPrimeNumberP() {
        if (this.factors != null) {
            return this.factors.getP();
        } else {
            throw new IllegalStateException("The prime number 'P' is unknown: public keys not generated here.");
        }
    }

    /**
     * @return the prime number 'Q' used a factor for calculating the public key.
     * @throws IllegalStateException in case the public key object was constructed with the values for N and E, without
     * passing over the generation process.
     */
    public long getPrimeNumberQ() {
        if (this.factors != null) {
            return this.factors.getQ();
        } else {
            throw new IllegalStateException("The prime number 'Q' is unknown: public keys not generated here.");
        }
    }

    /**
     * @return the public key E
     */
    public long getKeyE() {
        return this.e;
    }

    /**
     * @return the public key N
     */
    public long getKeyN() {
        return this.n;
    }
    
    /**
     * @return the value of the FI value
     */
    public double getFactorFI() {
        if (this.fi != 0) {
            return this.fi;
        } else {
            throw new IllegalStateException("The FI number is unknown: public keys not generated here.");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RsaPublicKey)) {
            return false;
        } else {
            return ((RsaPublicKey)obj).e == this.e && ((RsaPublicKey)obj).n == this.n;
        }
    }

    @Override
    public int hashCode() {
        return 31 * new Long(this.e).hashCode() + 17 * new Long(this.n).hashCode();
    }
}

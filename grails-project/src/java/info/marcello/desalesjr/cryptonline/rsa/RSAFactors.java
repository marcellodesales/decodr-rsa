package info.marcello.desalesjr.cryptonline.rsa;

import java.util.ArrayList;
import java.util.List;

import info.marcello.desalesjr.cryptonline.rsa.Algebra;
import static info.marcello.desalesjr.cryptonline.rsa.Rsa.DECIMAL_FORMATTER;

/**
 * The RSA Factos hold the prime numbers for the RSA algorithm based on public key. 
 * 
 * The RSA Factors is thread-safe and immutable.
 * 
 * @author Marcello de Sales (marcello.desales@gmail.com)
 * 
 */
public final class RSAFactors {

    /**
     * The prime number P
     */
    private final long p;

    /**
     * The prime number q
     */
    private final long q;

    /**
     * The private log for the calculator
     */
    private final List<String> log = new ArrayList<String>();

    private RSAFactors(long p, long q) {
        this.p = p;
        this.q = q;
        this.log.add(Rsa.LOG_ARROW + "Configuring random prime numbers");
        this.log.add("        P = " + DECIMAL_FORMATTER.format(p));
        this.log.add("        Q = " + DECIMAL_FORMATTER.format(q));
    }

    /**
     * Factory method to create a new instance of the RSAFactors based in the given prime numbers
     * 
     * @param p is a prime number
     * @param q is a prime number
     * @return a new instance of RSAFactos
     * @throws NotAPrimeNumberException if one or both given prime numbers is/are not prime
     */
    public static RSAFactors newInstance(long p, long q) throws NotAPrimeNumberException {
        if (!Algebra.SINGLETON.isPrime(p)) {
            throw new NotAPrimeNumberException(p);
        }
        if (!Algebra.SINGLETON.isPrime(q)) {
            throw new NotAPrimeNumberException(q);
        }
        return new RSAFactors(p, q);
    }

    /**
     * @return a new instance of RSAFactors with random prime numbers
     */
    public static RSAFactors newInstance() {
        String hasE = "E";
        long p = 0, q = 0;
        while (hasE.indexOf("E") != -1) {
            p = Algebra.SINGLETON.getRandomPrime(15000);
            q = Algebra.SINGLETON.getRandomPrime(15000);
            hasE = String.valueOf(p * q);
        }
        return new RSAFactors(p, q);
    }

    /**
     * @return the value of p
     */
    public long getP() {
        return p;
    }

    /**
     * @return the value of q
     */
    public long getQ() {
        return q;
    }
}

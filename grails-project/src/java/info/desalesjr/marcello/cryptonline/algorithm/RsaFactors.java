package info.desalesjr.marcello.cryptonline.algorithm;

import java.util.ArrayList;
import java.util.List;

import info.desalesjr.marcello.cryptonline.algorithm.Algebra;
import static info.desalesjr.marcello.cryptonline.algorithm.Rsa.DECIMAL_FORMATTER;

/**
 * The RSA Factors hold the prime numbers for the RSA algorithm based on public key.
 * 
 * The RSA Factors is thread-safe and immutable.
 * 
 * @author Marcello de Sales (marcello.desales@gmail.com)
 * 
 */
public final class RsaFactors {

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

    private RsaFactors(long p, long q) {
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
    public static RsaFactors newInstance(long p, long q) throws NotAPrimeNumberException {
        if (!Algebra.SINGLETON.isPrime(p)) {
            throw new NotAPrimeNumberException(p);
        }
        if (!Algebra.SINGLETON.isPrime(q)) {
            throw new NotAPrimeNumberException(q);
        }
        return new RsaFactors(p, q);
    }

    /**
     * @return a new instance of RSAFactors with random prime numbers
     */
    public static RsaFactors newInstance() {
        String hasE = "E";
        long p = 0, q = 0;
        while (hasE.indexOf("E") != -1) {
            p = Algebra.SINGLETON.getRandomPrime(15000);
            q = Algebra.SINGLETON.getRandomPrime(15000);
            hasE = String.valueOf(p * q);
        }
        return new RsaFactors(p, q);
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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RsaFactors)) {
            return false;
        } else
            return (((RsaFactors) (obj)).getP() == this.p && ((RsaFactors) (obj)).getQ() == this.q);
    }

    @Override
    public int hashCode() {
        return 35 + new Long(this.p).hashCode() + 71 + new Long(this.q).hashCode();
    }
    
    /**
     * @return The log entries from the factos creation
     */
    public String[] getLogEntries() {
        String[] logEntries = new String[this.log.size()];
        int i = -1;
        for (String entry : this.log) {
            logEntries[++i] = entry;
        }
        return logEntries;
    }
}

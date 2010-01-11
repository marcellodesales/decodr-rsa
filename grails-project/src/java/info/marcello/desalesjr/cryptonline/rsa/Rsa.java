package info.marcello.desalesjr.cryptonline.rsa;

import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * The RSA class represents the entire algorithm with the all the keys. During its execution, a log is maintained for
 * the explanation.
 * 
 * Immutable class.
 * 
 * @author Marcello de Sales (marcello.desales@gmail.com)
 */
public final class Rsa {

    /**
     * The default formatter for decimal numbers.
     */
    public final static DecimalFormat DECIMAL_FORMATTER = new DecimalFormat("0");
    /**
     * The delimiter used in different logs
     */
    public static final String DELIMITER = "-";
    /**
     * The delimiter for the log
     */
    public static final String LOG_ARROW = "-> ";

    /**
     * The reference to the private key. It also contains the reference to the public key.
     */
    private final RSAPrivateKey privateKey;

    /**
     * The private log for the calculator
     */
    private final List<String> log = new ArrayList<String>();

    /**
     * Constructs a new RSA with a random configuration of keys
     */
    private Rsa(RSAPrivateKey keys) {
        this.privateKey = keys;
    }

    /**
     * Constructs a new RSA object with the given prime numbers p and q.
     * 
     * @param p is a prime number
     * @param q is a prime number
     * @throws NotAPrimeNumberException if any of the numbers are not prime.
     */
    private Rsa(int p, int q) throws NotAPrimeNumberException {
        RSAPublicKey publicKey = RSAPublicKey.newInstance(RSAFactors.newInstance(p, q));
        this.privateKey = RSAPrivateKey.newInstance(publicKey);
    }

    /**
     * @return a randomly generated RSA public and private Keys
     */
    public static Rsa newInstance() {
        RSAPrivateKey randomPrivateKey = RSAPrivateKey.newInstance(RSAPublicKey.newInstance());
        return new Rsa(randomPrivateKey);
    }

    public long getPublicKeyN() {
        return this.privateKey.getRSAPublicKey().getKeyN();
    }

    public long getPublicKeyE() {
        return this.privateKey.getRSAPublicKey().getKeyE();
    }

    public double getPrivateKeyD() {
        return this.privateKey.getPrivateKeyD();
    }

    public void printAll(PrintStream printStream) {
        printStream.println("#### All RSA Information ####");
        printStream.println();
        printStream.println("Prime P: " + (int) this.privateKey.getRSAPublicKey().getPrimeNumberP());
        printStream.println("Prime Q: " + (int) this.privateKey.getRSAPublicKey().getPrimeNumberQ());
        printStream.println();
        printStream.println("Public Key (N, E) = (" + (int) this.getPublicKeyN() + ", " + (int) this.getPublicKeyE() + ")");
        printStream.println("Private Key (N, D) = (" + (int) this.getPublicKeyN() + ", " + (int) this.getPrivateKeyD() + ")");
        printStream.println();
    }

    /**
     * Prints the execution log on the given print stream
     * @param printStream
     */
    public void printLog(PrintStream printStream) {
        for (String logEntry : this.log) {
            printStream.println(logEntry);
        }
    }

    public static void main(String[] args) {
        Rsa rsa = Rsa.newInstance();
        rsa.printLog(System.out);
        rsa.printAll(System.out);

        String origem = "Marcello de Sales: because solving problems is addicting!";

        RsaSender sender = RsaSender.newInstance(origem, rsa);
        sender.printLog(System.out);

        RsaReceiver receiver = RsaReceiver.newInstance(sender.getEncryptedMessage(), rsa);
        receiver.printLog(System.out);
    }
}

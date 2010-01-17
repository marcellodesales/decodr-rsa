package info.desalesjr.marcello.cryptonline.algorithm;

/**
 * Exception thrown when a given number is not prime
 * @author Marcello de Sales (marcello.desales@gmail.com)
 */
public class NotAPrimeNumberException extends Exception {

    /**
     * Serial version
     */
    private static final long serialVersionUID = 1L;

    public NotAPrimeNumberException(long number) {
        super("The number " + number + " is not a prime number");
    }

}

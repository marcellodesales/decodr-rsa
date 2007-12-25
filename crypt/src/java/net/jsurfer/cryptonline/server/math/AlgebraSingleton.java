/**
 * Algebra.java
 *
 * @author Created by Marcello Junior
 */

package net.jsurfer.cryptonline.server.math;

public class AlgebraSingleton {

    private static AlgebraSingleton singleton = null;

    private AlgebraSingleton() {
    }

    public static AlgebraSingleton getInstance() {
        if (AlgebraSingleton.singleton == null) {
            AlgebraSingleton.singleton = new AlgebraSingleton();
        }
        return AlgebraSingleton.singleton;
    }

    /**
     * @param a
     *            regular number to be divided by b
     * @param b
     *            regular number that divides a
     * @return the quotient of a divided by b
     */
    public double getQuotient(double a, double b) {
        double q = 1;
        while (b * q <= a)
            q++;
        q--;
        return q;
    }

    /**
     * @param a
     *            regular number to be divided by b
     * @param b
     *            regular number that divides a
     * @return The reminder of the division of a per b
     */
    public double getReminder(double a, double b) {
        double q = 1;
        while (b * q <= a)
            q++;
        q--;
        return (a - b * q);
    }

    /**
     * @param x
     *            is a given positive number
     * @return If the given x number is prime
     */
    public boolean isPrime(double x) {
        double v = x;
        double i = 2;
        while ((this.getReminder(v, i) != 0) && ((i * i) <= v))
            i++;
        return (this.getReminder(v, i) != 0);
    }

    /**
     * @param p
     *            is a positive double number
     * @param q
     *            is a positive double number
     * @return The Common Maximum Divisor (CMD) of p and q
     */
    public double getMDC(double p, double q) {
        double r = this.getReminder(p, q);
        while (r > 0) {
            p = q;
            q = r;
            r = this.getReminder(p, q);
        }
        return q;
    }

    /**
     * @param p
     *            is a positive number
     * @param q
     *            is a positive number
     * @return the "Euler" between 2 numbers (p - 1) * (q - 1).
     */
    public double getEuler(double p, double q) {
        return (p - 1) * (q - 1);
    }

    /**
     * @param a
     *            is a positive number
     * @param e
     *            is a positive number
     * @param n
     *            is a positive number
     * @return the a power e module n: a ^ e mod N
     */
    public double getPowerModuleN(double a, double e, double n) {
        double pp = 1;
        while (e != 0) {
            if ((e % 2) != 0)
                pp = a * pp % n;
            e = this.getQuotient(e, 2);
            a = a * a % n;
        }
        return pp;
    }

    /**
     * @param range
     *            is a positive number defined for the range.
     * @return a random number on the given range
     */
    public int getARandomInteger(int range) {
        return (int) (Math.random() * --range + 1);
    }

    /**
     * @param input
     *            based on a given input, a positive number.
     * @param range
     *            is a range for the prime number. A good number is 2000.
     * @param index is the index
     * 
     * @return the prime number based on the input and the range.
     * 
     */
    public double getRandomPrime(int range) {
        double number;
        if (range < 1) {
            throw new IllegalArgumentException(
                    "Range parameter must be a positive number >= 2");
        }
        if (range <= 2) {
            return range;
        }
        do {
            number = this.getARandomInteger(this.getARandomInteger(range)) + 
                          this.getARandomInteger(this.getARandomInteger(range/2));
        } while (!this.isPrime(number));
        return number;
    }
}
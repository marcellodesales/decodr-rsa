/**
 * Algebra.java
 * 
 * @author Created by Marcello Junior
 */

package info.marcello.desalesjr.cryptonline.rsa;

/**
 * Implements the algebra formulas. Singleton Enum
 * 
 * @author Marcello de Sales (marcello.desales@gmail.com)
 * 
 */
public enum Algebra {

    SINGLETON;

    /**
     * @param a regular number to be divided by b
     * @param b regular number that divides a
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
     * @param a regular number to be divided by b
     * @param b regular number that divides a
     * @return The reminder of the division of a per b
     */
    public long getReminder(long a, long b) {
        long q = 1;
        while (b * q <= a)
            q++;
        q--;
        return (a - b * q);
    }

    /**
     * @param x is a given positive number
     * @return If the given x number is prime
     */
    public boolean isPrime(long x) {
        long v = x;
        long i = 2;
        while ((this.getReminder(v, i) != 0) && ((i * i) <= v))
            i++;
        return (this.getReminder(v, i) != 0);
    }

    /**
     * @param p is a positive double number
     * @param q is a positive double number
     * @return The Common Maximum Divisor (CMD) of p and q
     */
    public long getMDC(long p, long q) {
        long r = this.getReminder(p, q);
        while (r > 0) {
            p = q;
            q = r;
            r = this.getReminder(p, q);
        }
        return q;
    }
    
    public static double getExtendedMDC(double p, double q) {
        double mdc, t = 0, u = 0;
        double a = p;
        double b = q;

        if (b == 0) {
            mdc = a;
            t = 1;
            u = 0;
        }

        double x2 = 1;
        double x1 = 0;
        double y2 = 0;
        double y1 = 1;
        double quoc, r;
        while (b > 0) {
            quoc = (double)Algebra.SINGLETON.getQuotient((long)a, (long)b);
            r = a - b * quoc;
            t = x2 - quoc * x1;
            u = y2 - quoc * y1;
            x2 = x1;
            x1 = t;
            y2 = y1;
            y1 = u;

            a = b;
            b = r;
        }
        mdc = a;
        t = x2;
        u = y2;
        return mdc;
    }

    /**
     * @param p is a positive number
     * @param q is a positive number
     * @return the "Euler" between 2 numbers (p - 1) * (q - 1).
     */
    public long getEuler(long p, long q) {
        return (p - 1) * (q - 1);
    }

    /**
     * @param a is a positive number
     * @param e is a positive number
     * @param n is a positive number
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
     * @param range is a positive number defined for the range.
     * @return a random number on the given range
     */
    public int getARandomInteger(int range) {
        return (int) (Math.random() * --range + 1);
    }

    /**
     * @param input based on a given input, a positive number.
     * @param range is a range for the prime number. A good number is 2000.
     * @param index is the index
     * @return the prime number based on the input and the range.
     * 
     */
    public long getRandomPrime(int range) {
        long number;
        if (range < 1) {
            throw new IllegalArgumentException("Range parameter must be a positive number >= 2");
        }
        if (range <= 2) {
            return range;
        }
        do {
            number = this.getARandomInteger(this.getARandomInteger(range))
                    + this.getARandomInteger(this.getARandomInteger(range / 2));
        } while (!this.isPrime(number));
        return number;
    }
}
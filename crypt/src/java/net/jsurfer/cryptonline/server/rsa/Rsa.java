/**
 * Rsa.java
 *
 * @author Created by Marcello Junior
 */

package net.jsurfer.cryptonline.server.rsa;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Rsa {

    public static final String DELIMITER = "-";
    public static final String LOG_ARROW = "-> ";
    private static final Algebra algebra = Algebra.getInstance();

    private double p;
    private double q;
    private double e;
    private double n;
    private double d;
    private double fi;

    private double t, u;

    private List<String> log = new ArrayList<String>();;
    private final static DecimalFormat decimalFormat = new DecimalFormat("0");
    
    public Rsa() {
    }
    
    public void changeKeys() {
        this.setNewPrimes();
        this.setNewPublicKeys();
        this.setNewPrivateKeys();
    }

    public Rsa(int p, int q) {
        this.p = (double)p;
        this.q = (double)q;
        this.setNewPublicKeys();
        this.setNewPrivateKeys();
    }

    public double getPublicKey_N() {
        return this.n;
    }

    public double getPublicKey_E() {
        return this.e;
    }

    public double getPrivateKey_D() {
        return this.d;
    }

    public void setPublicKey_N(double n) {
        this.n = n;
    }

    public void setPublicKey_E(double e) {
        this.e = e;
    }

    public void setPrivateKey_D(double d) {
        this.d = d;
    }

    private void setNewPrimes() {
        String hasE = "E";

        while (hasE.indexOf("E") != -1) {
            this.p = algebra.getRandomPrime(15000);
            this.q = algebra.getRandomPrime(15000);
            hasE = String.valueOf(p * q);
        }

        this.log.add(Rsa.LOG_ARROW + "Configuring random prime numbers");
        this.log.add("        P = " + decimalFormat.format(this.p));
        this.log.add("        Q = " + decimalFormat.format(this.q));
    }

    private void setNewPublicKeys() {
        //What was observed was that when the prime numbers are small, the reverse function fails... The new primes
        //must be big enough to make N a big number.
        this.n = this.p * this.q;
        this.log.add(Rsa.LOG_ARROW + "Calculating public keys");
        this.log.add("          N = P * Q; N = "
                + decimalFormat.format(this.n));

        this.e = this.getPublicKeyE();
        this.log.add("          E = " + decimalFormat.format(this.e));
        this.log.add("");

        this.log.add("       Public Key (N,E) = ("
                + decimalFormat.format(this.n) + " , "
                + decimalFormat.format(this.e) + ")");
        this.log.add("");
    }

    private void setNewPrivateKeys() {
        this.log.add(Rsa.LOG_ARROW + "Calculating private keys");
        this.d = this.getDInverseE();
        this.log.add("");
        this.log.add("      Private Key (N,D) = ("
                + decimalFormat.format(this.n) + ", "
                + decimalFormat.format(this.d) + ");");
    }

    private double getPublicKeyE() {
        // FI = (P-1) * (Q-1);
        this.fi = algebra.getEuler(this.p, this.q);

        this.log.add(Rsa.LOG_ARROW + "FI = (P-1) * (Q-1); FI = " + decimalFormat.format(this.fi));
        this.log.add("");
        this.log.add(Rsa.LOG_ARROW + "Calculating (E)");

        double aux = 2;
        double mdc = algebra.getMDC(aux, this.fi);
        this.log.add("           While MCD(n >= 2 , "
                + decimalFormat.format(fi) + ") != 1");
        while (mdc != 1) {

            this.log.add("           MCD(" + decimalFormat.format(aux)
                    + " , " + decimalFormat.format(fi) + ") = "
                    + decimalFormat.format(mdc));
            aux++;
            mdc = algebra.getMDC(aux, this.fi);
        }
        this.log.add("           MCD(" + decimalFormat.format(aux) + " , "
                + decimalFormat.format(fi) + ") = "
                + decimalFormat.format(mdc) + " Correct!");
        return aux;
    }

    public double getDInverseE() {
        double u1 = 1;
        double u2 = 0;
        double u3 = this.fi;
        double v1 = 0;
        double v2 = 1;
        double v3 = this.e;
        double t1, t2, t3, vv, qq;

        this.log.add("         Initializing (p1,p2,p3) = (1, 0 , FI(n))");
        this.log.add("         Initializing (q1,q2,q3) = (0, 1 ,  E  ))");
        this.log.add("         While q3 != 0");
        this.log.add("             quoc = p3 / q3");
        this.log
                .add("             (t1,t2,t3) = (p1,p2,p3) - quoc * (q1,q2,q3)");
        this.log.add("             After, arrange the values:");
        this.log.add("             (p1,p2,p3) = (q1,q2,q3)");
        this.log.add("             (q1,q2,q3) = (t1,t2,t3)");

        while (v3 > 0) {
            this.log.add("");
            this.log.add("           (" + decimalFormat.format(v3)
                    + " <> 0) , then:");
            qq = algebra.getQuotient(u3, v3);

            this.log.add("             quoc = " + decimalFormat.format(u3)
                    + " / " + decimalFormat.format(v3) + " = "
                    + decimalFormat.format(qq));

            t1 = u1 - qq * v1;
            t2 = u2 - qq * v2;
            t3 = u3 - qq * v3;

            u1 = v1;
            u2 = v2;
            u3 = v3;
            v1 = t1;
            v2 = t2;
            v3 = t3;

            this.log.add("             (t1,t2,t3) = ("
                    + decimalFormat.format(u1) + ","
                    + decimalFormat.format(u2) + ","
                    + decimalFormat.format(u3) + ") - "
                    + decimalFormat.format(qq) + " * ("
                    + decimalFormat.format(v1) + ","
                    + decimalFormat.format(v2) + ","
                    + decimalFormat.format(v3) + ") = ("
                    + decimalFormat.format(t1) + ","
                    + decimalFormat.format(t2) + ","
                    + decimalFormat.format(t3) + ")");
            this.log.add("             (p1,p2,p3) = ("
                    + decimalFormat.format(v1) + ","
                    + decimalFormat.format(v2) + ","
                    + decimalFormat.format(v3) + ")");

            this.log.add("             (q1,q2,q3) = ("
                    + decimalFormat.format(t1) + ","
                    + decimalFormat.format(t2) + ","
                    + decimalFormat.format(t3) + ")");
        }
        this.log.add("");
        this.log
                .add("         q3 is zero(0). Now, verify the value of p2. In case of negative, invert it by summing"
                        + " it with FI. (represent the negative number of z(n) by a positive.)");
        this.log.add("");
        this.log.add("         u2 = " + decimalFormat.format(u2) + ";");

        vv = u2;
        double inverse;
        if (vv < 0) {
            inverse = vv + this.fi;
            this.log.add("          Since u2 is negative, we have:");
            this.log.add("          D = u2 + FI; D = "
                    + decimalFormat.format(u2) + " + "
                    + decimalFormat.format(this.fi) + " = "
                    + decimalFormat.format(inverse));
        } else {
            inverse = vv;
            this.log.add("         D = u2; D = "
                    + decimalFormat.format(u2));
        }
        return inverse;
    }

    public double getExtendedMDC(double p, double q) {
        double mdc;
        double a = p;
        double b = q;

        if (b == 0) {
            mdc = a;
            this.t = 1;
            this.u = 0;
        }

        double x2 = 1;
        double x1 = 0;
        double y2 = 0;
        double y1 = 1;
        double quoc, r;
        while (b > 0) {
            quoc = algebra.getQuotient(a, b);
            r = a - b * quoc;
            this.t = x2 - quoc * x1;
            this.u = y2 - quoc * y1;
            x2 = x1;
            x1 = this.t;
            y2 = y1;
            y1 = this.u;

            a = b;
            b = r;
        }
        mdc = a;
        this.t = x2;
        this.u = y2;
        return mdc;
    }

    public void printAll() {
        System.out.println("#### All RSA Information ####");
        System.out.println();
        System.out.println("Prime P: " + (int)this.p);
        System.out.println("Prime Q: " + (int)this.q);
        System.out.println();
        System.out.println("Public Key (N, E) = (" + (int)this.n + ", " + (int)this.e
                + ")");
        System.out.println("Private Key (N, D) = (" + (int)this.n + ", " + (int)this.d
                + ")");
        System.out.println();
        // System.out.println("MDC extendido => a * (t) + b * (u) = mdc ");
        // System.out.println(this.p+" * ("+this.t+") + "+this.q+" *
        // ("+this.u+") = "+this.getExtendedMDC(this.p,this.q));
    }

    public void printLog() {
        for (String logEntry : this.log) {
            System.out.println(logEntry);
        }
    }

    public Iterator<String> getLog() {
        return this.log.iterator();
    }

    public static void main(String[] args) {
        Rsa rsa = new Rsa();
        rsa.printLog();
        rsa.printAll();

        String origem = "I'd love to be admitted to the MS program at the Computer Science department at San Francisco " +
        		"State University!";

        RsaSender sender = new RsaSender(origem, rsa);
        sender.printLog();
        System.out.println("Ascii: "+sender.getAsciiMessage());
        System.out.println("Encrypted: "+sender.getEncryptedMessage());

        RsaReceiver receiver = new RsaReceiver(sender.getEncryptedMessage(), rsa);
        receiver.printLog();
    }

    /**
     * @return the p
     */
    public double getP() {
        return p;
    }

    /**
     * @return the q
     */
    public double getQ() {
        return q;
    }
}

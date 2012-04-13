package com.signr.rsa.core;

import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.annotation.concurrent.Immutable;

/**
 * The RSA class represents the entire algorithm with the all the keys. During its execution, a log
 * is maintained for the explanation.
 * 
 * Immutable class.
 * 
 * @author Marcello de Sales (marcello.desales@gmail.com)
 */
@Immutable
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
  private final RsaPrivateKey privateKey;
  /**
   * The private log for the calculator
   */
  private final List<String> log = new ArrayList<>();

  /**
   * Constructs a new RSA with a random configuration of keys
   */
  private Rsa(RsaPrivateKey keys) {
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
    RsaPublicKey publicKey = RsaPublicKey.newInstance(RsaFactors.newInstance(p, q));
    this.privateKey = RsaPrivateKey.newInstance(publicKey);
  }

  /**
   * Factory method for creating a complete RSA object based on a random value of all the keys.
   * 
   * @return a new instance of the Rsa object.
   */
  public static Rsa newInstance() {
    RsaPrivateKey privateKey = RsaPrivateKey.newInstance(RsaPublicKey.newInstance());
    return new Rsa(privateKey);
  }

  /**
   * Factory method to create a new instance based on the prime numbers P and Q.
   * 
   * @param primeP is a large prime number.
   * @param primeQ is a smaller prime number.
   * @return a new instance of Rsa based on the two prime numbers.
   * @throws NotAPrimeNumberException in case any of the given prime numbers is not a prime number.
   */
  public static Rsa newInstance(int primeP, int primeQ) throws NotAPrimeNumberException {
    return new Rsa(primeP, primeQ);
  }

  /**
   * @return the reference to the public key
   */
  public RsaPublicKey getPublicKey() {
    return this.privateKey.getRSAPublicKey();
  }

  /**
   * @return the reference to the private key
   */
  public RsaPrivateKey getPrivateKey() {
    return this.privateKey;
  }

  /**
   * Prints the keys associated with this RSA object in the given PrintStream instance.
   * 
   * @param printStream is the print stream to be print the information.
   */
  public void printKeys(PrintStream printStream) {
    printStream.println("#### RSA Public Key Creation Process ####");
    printStream.println();
    for (String logEntry : this.getPublicKey().getLogEntries()) {
      printStream.println(logEntry);
    }
    printStream.println();
    printStream.println("#### RSA Private Key Creation Process ####");
    for (String logEntry : this.getPrivateKey().getLogEntries()) {
      printStream.println(logEntry);
    }
    printStream.println();
    printStream.println("#### RSA Keys Summary ####");
    printStream.println();
    printStream.println("Public Key (N, E) = (" + (int) this.getPublicKey().getKeyN() + ", "
        + (int) this.getPublicKey().getKeyE() + ")");
    printStream.println("Private Key (N, D) = (" + (int) this.getPublicKey().getKeyN() + ", "
        + (int) this.getPrivateKey().getKeyD() + ")");
    printStream.println();
  }

  /**
   * Prints the execution log on the given print stream
   * 
   * @param printStream
   */
  public void printLog(PrintStream printStream) {
    for (String logEntry : this.log) {
      printStream.println(logEntry);
    }
  }

  public static void main(String[] args) {
    System.out.println("########## RSA Signr ###########");
    System.out.println("Click in any key to see the creation of a random public/private keys.");

    Scanner input = new Scanner(System.in);
    input.nextLine();

    Rsa marcellosRsa = Rsa.newInstance();
    marcellosRsa.printKeys(System.out);

    System.out.print("Now, type the message to be encrypted: ");
    String origem = input.nextLine();

    System.out.println();
    System.out.print("The Encoder will encrypt your message...");
    RsaEncoder sender = RsaEncoder.newInstance(origem, marcellosRsa.getPublicKey());

    System.out.print("Press any key to see the algorithm...");
    input.nextLine();

    sender.printLog(System.out);

    System.out.println();
    System.out.print("Now that you have seen the encryption, let's proceed with the decoding... Press any key...");
    input.nextLine();

    RsaDecoder receiver = RsaDecoder.newInstance(sender.getEncryptedMessage(), marcellosRsa.getPrivateKey());
    receiver.printLog(System.out);
  }
}

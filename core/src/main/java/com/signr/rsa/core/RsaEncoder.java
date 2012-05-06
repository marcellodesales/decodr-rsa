package com.signr.rsa.core;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.concurrent.Immutable;

import static com.signr.rsa.core.Rsa.DECIMAL_FORMATTER;

/**
 * The Rsa Encoder implements the encryption machine.
 * 
 * This is a thread-safe immutable class.
 * 
 * @author Marcello de Sales (marcello.desales@gmail.com)
 * 
 */
@Immutable
public final class RsaEncoder {

  /**
   * The original message to be sent
   */
  private final String originalMessage;
  /**
   * The encrypted message as a result of the rsa algorithm
   */
  private final String encryptedMessage;
  /**
   * The execution log
   */
  private final List<String> log = new ArrayList<>();
  /**
   * The RSAPublic key used to encrypt the original message
   */
  private final RsaPublicKey publicKey;

  /**
   * Constructs a new RsaSender with the originalMessage along with the Rsa structure
   * 
   * @param originalMessage is the original human-readable message
   * @param publicKey is the RSA object holding the public and private keys
   */
  private RsaEncoder(String givenOriginalMessage, RsaPublicKey givenPublicKey) {
    publicKey = givenPublicKey;
    originalMessage = givenOriginalMessage;
    encryptedMessage = encryptMessage(originalMessage);

    log.add("");
    log.add(Rsa.LOG_ARROW + "Encrypted Message");
    log.add(encryptedMessage);
  }

  /**
   * @param originalMessage is the original message to be encoded
   * @param publicKey is the key used to encrypt the given original message
   * @return a new instance of RsaSender
   */
  public static RsaEncoder newInstance(String originalMessage, RsaPublicKey publicKey) {
    return new RsaEncoder(originalMessage, publicKey);
  }

  /**
   * @return The original message
   */
  public String getOriginalMessage() {
    return this.originalMessage;
  }

  /**
   * @return the encrypted message
   */
  public String getEncryptedMessage() {
    return this.encryptedMessage;
  }

  /**
   * @param originalMessage is the human-readable message
   * @return the encrypted message based on the original human-readable message and the RSA private
   *         and public keys
   */
  private String encryptMessage(String originalMessage) {
    log.add("");
    log.add("#### Sending a message ####");
    log.add("");
    log.add(Rsa.LOG_ARROW + "Original Message");
    log.add(originalMessage);
    log.add("");
    log.add(Rsa.LOG_ARROW + "Setting the receiver's public key");
    log.add("(N , E) = (" + DECIMAL_FORMATTER.format(this.publicKey.getKeyN()) + " , "
        + DECIMAL_FORMATTER.format(this.publicKey.getKeyE()) + ")");
    log.add("");
    log.add(Rsa.LOG_ARROW + "Transforming the message to ASCII code");
    String asciiMessage = this.encodeStringToAscii(originalMessage);
    log.add(asciiMessage);
    log.add("");
    log.add(Rsa.LOG_ARROW + "Configuring randomly selected blocks from the ASCII message");
    List<Integer> asciiBlocks = this.getAsciiBlocks(asciiMessage);
    return this.encryptBlocks(asciiBlocks);
  }

  /**
   * @param widestring is the string before encryption
   * @return the ASCII representation a string
   */
  private String encodeStringToAscii(String widestring) {
    StringBuilder ascii = new StringBuilder();
    for (int i = 0; i < widestring.length(); i++) {
      ascii.append((int) widestring.charAt(i) + 100);
    }
    System.out.println("" + ascii);
    return ascii.toString();
  }

  /**
   * Returns the set of blocks of the ASCII representation in a random way 1241355334 =>
   * [1241][355][3][34]. It can't begin with 0's.
   */
  private List<Integer> getAsciiBlocks(String asciiMessage) {
    int copyLength = -1;
    List<Integer> blocks = new ArrayList<>();
    String stringN = String.valueOf(this.publicKey.getKeyN());
    int maxBlockLength = stringN.length() - 3;

    while (asciiMessage.length() > 0) {
      int initialSize =
          asciiMessage.length() <= maxBlockLength ? asciiMessage.length() : maxBlockLength;
      String potentialBlock = asciiMessage.substring(0, initialSize);
      int $0blockIndex = potentialBlock.indexOf("00"); // blocks like 20010
      if ($0blockIndex > -1) {
        potentialBlock = asciiMessage.substring(0, $0blockIndex + 2);

      } else {
        $0blockIndex = potentialBlock.indexOf("0"); // blocks like 51120
        if ($0blockIndex > -1) {
          potentialBlock = asciiMessage.substring(0, $0blockIndex + 1);
          if (asciiMessage.length() > maxBlockLength) {
            if (asciiMessage.charAt($0blockIndex + 1) == '0') { // 51120 03992
              copyLength = Algebra.SINGLETON.getARandomInteger(maxBlockLength - 2);
              potentialBlock = asciiMessage.substring(0, copyLength);
            }
          }
        }
        if (asciiMessage.length() <= maxBlockLength + 1) {
          if (asciiMessage.endsWith("0")) { // end of the string with ending 0s, smaller blocks
            copyLength = Algebra.SINGLETON.getARandomInteger(asciiMessage.length()-3);
            potentialBlock = asciiMessage.substring(0, copyLength);
          }
        }
      }
      if (!potentialBlock.contains("0")) {
        copyLength = Algebra.SINGLETON.getARandomInteger(maxBlockLength); // just get a random block

        // when the string is smaller than the largest block
        if (copyLength > asciiMessage.length()) {
          copyLength = asciiMessage.length();
        }
        potentialBlock = asciiMessage.substring(0, copyLength);
      }
      if (copyLength == -1) { // if nothing applied
        copyLength = 0;
      }
      asciiMessage = asciiMessage.substring(potentialBlock.length(), asciiMessage.length());
      blocks.add(new Integer(potentialBlock));
    }
    return blocks;
  }

  /**
   * Encrypts a block with the Power Module N algorithm [1241] => 343434
   * 
   * @param x is the number representing a block
   * @return the result of the Power Module N algorithm
   */
  private double encryptBlock(int x) {
    double encryptedBlock =
        Algebra.SINGLETON.getPowerModuleN(x, this.publicKey.getKeyE(), this.publicKey.getKeyN());
    this.log.add("Block(" + DECIMAL_FORMATTER.format(x) + ") = " + DECIMAL_FORMATTER.format(x)
        + " ^ " + DECIMAL_FORMATTER.format(this.publicKey.getKeyE()) + " mod "
        + DECIMAL_FORMATTER.format(this.publicKey.getKeyN()) + " = "
        + DECIMAL_FORMATTER.format(encryptedBlock));
    return encryptedBlock;
  }

  /**
   * Returns the set of encrypted blocks of the ascii representation applying the Power Module N
   * algorithm Coded blocks: [1241][355][3][34] ==> Encrypted Final message: 343434-43552-43544-3435
   * http://en.wikipedia.org/wiki/Modular_arithmetic
   * 
   * @param codedBlocks is the list of coded blocks to be encrypted
   * @return the encrypted message based on the Power Module N algorithm based on the coded blocks
   */
  private String encryptBlocks(List<Integer> codedBlocks) {
    log.add("Block(x) = x ^ E mod N");
    log.add("");

    double encryptedBlock;
    StringBuilder code = new StringBuilder();

    for (int blockValue : codedBlocks) {
      encryptedBlock = this.encryptBlock(blockValue);
      code.append(DECIMAL_FORMATTER.format(encryptedBlock) + Rsa.DELIMITER);
    }
    return code.toString().substring(0, code.toString().length() - 1);
  }

  /**
   * Prints the log into a given print stream
   * 
   * @param printStream is a stream where the log will be printed. System.out,
   *        Servlet.getPrintWriter
   */
  public void printLog(PrintStream printStream) {
    for (String logEntry : this.log) {
      printStream.println(logEntry);
    }
  }

  /**
   * @return the log entries for the creation of the encoding process.
   */
  public String[] getLog() {
    List<String> logEntries = new ArrayList<>(log.size());
    for (String factorEntry : log) {
      logEntries.add(factorEntry);
    }
    return logEntries.toArray(new String[logEntries.size()]);
  }
}

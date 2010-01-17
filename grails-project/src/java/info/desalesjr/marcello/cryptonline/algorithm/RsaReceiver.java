/**
 * RsaReceiver.java
 * 
 * @author Created by Omnicore CodeGuide
 */

package info.desalesjr.marcello.cryptonline.algorithm;

import static info.desalesjr.marcello.cryptonline.algorithm.Rsa.DECIMAL_FORMATTER;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * The Rsa Receiver is used to decrypt a encoded messages.
 * 
 * This is a thread-safe immutable class.
 * 
 * @author Marcello de Sales (marcello.desales@gmail.com)
 * 
 */
public final class RsaReceiver {

    /**
     * The original message received
     */
    private String decryptedMessage;
    /**
     * The encrypted message as a result of the rsa algorithm
     * */
    private String encryptedMessage;
    /**
     * The privateKey to decode the encrypted message
     * */
    private RsaPrivateKey privateKey;
    /**
     * Log used to output the messages
     */
    private List<String> log = new ArrayList<String>();

    /**
     * Constructs a new RsaSender with the originalMessage along with the Rsa structure
     * 
     * @param encryptedMessage is the message encrypted in blocks.
     * @param privateKey is the private key to decode the given message
     */
    private RsaReceiver(String encryptedMessage, RsaPrivateKey privateKey) {
        this.privateKey = privateKey;
        this.encryptedMessage = encryptedMessage;
        this.decryptedMessage = this.getDecryptedMessage(this.encryptedMessage);
        this.log.add("");
        this.log.add(Rsa.LOG_ARROW + "Original Message");
        this.log.add(this.decryptedMessage);
    }

    /**
     * @param encryptedMessage
     * @param privateKey is the instance of a private key
     * @return a new instance of the RsaReceiver based on the rsa.
     */
    public static RsaReceiver newInstance(String encryptedMessage, RsaPrivateKey privateKey) {
        return new RsaReceiver(encryptedMessage, privateKey);
    }

    public String getOriginalMessage() {
        return this.decryptedMessage;
    }

    public String getEncryptedMessage() {
        return this.encryptedMessage;
    }

    /**
     * @param encryptedMessage is the encrypted message
     * @return the original message sent
     */
    private String getDecryptedMessage(String encryptedMessage) {
        this.log.add("Receiving the message");
        this.log.add("");
        this.log.add(Rsa.LOG_ARROW + "Encrypted Message");
        this.log.add(encryptedMessage);
        this.log.add("");
        this.log.add(Rsa.LOG_ARROW + "Setting the private key");
        this.log.add("(N , D) = (" + DECIMAL_FORMATTER.format(this.privateKey.getRSAPublicKey().getKeyN()) + " , "
                + DECIMAL_FORMATTER.format(this.privateKey.getKeyD()) + ")");

        List<Integer> chriptedBlocks = this.getCryptedBlocks(encryptedMessage);
        this.log.add("");
        this.log.add(Rsa.LOG_ARROW + "Decripting each block");
        String dechriptedBlocks = this.getDeschriptedBlocksInAscii(chriptedBlocks);
        this.log.add("");
        this.log.add(Rsa.LOG_ARROW + "Complete message in ASCII");
        this.log.add(dechriptedBlocks);
        return this.getORiginalMessage(dechriptedBlocks);
    }

    /**
     * @param encryptedMessage is the encryption used
     * @return the the crypted blocks from given encrypted message
     */
    private List<Integer> getCryptedBlocks(String encryptedMessage) {
        List<Integer> chriptedBlocks = new ArrayList<Integer>();
        StringTokenizer tokenizer = new StringTokenizer(encryptedMessage, Rsa.DELIMITER);
        while (tokenizer.hasMoreTokens()) {
            String chriptedBlock = tokenizer.nextToken();
            chriptedBlocks.add(new Integer(chriptedBlock));
        }
        return chriptedBlocks;
    }

    /**
     * @param encryptedBlock decrypts a given encrypted block
     * @return the decrypted value for the given encrypted block
     */
    private double getDechriptedBlock(double encryptedBlock) {
        double dechriptedb = Algebra.SINGLETON.getPowerModuleN(encryptedBlock, this.privateKey.getKeyD(),
                this.privateKey.getRSAPublicKey().getKeyN());
        this.log.add("Ascii(" + DECIMAL_FORMATTER.format(encryptedBlock) + ") = "
                + DECIMAL_FORMATTER.format(encryptedBlock) + " ^ "
                + DECIMAL_FORMATTER.format(this.privateKey.getKeyD()) + " mod "
                + DECIMAL_FORMATTER.format(this.privateKey.getRSAPublicKey().getKeyN()) + " = "
                + DECIMAL_FORMATTER.format(dechriptedb));
        return dechriptedb;
    }

    /**
     * @param encryptedBlocks a collection of encrypted blocks
     * @return the decrypted block in ASCII value
     */
    private String getDeschriptedBlocksInAscii(List<Integer> encryptedBlocks) {
        this.log.add("Ascii(x) = x ^ D mod N");
        this.log.add("");

        StringBuilder asciiMessage = new StringBuilder();
        int dechriptedValue;
        for (int blockValue : encryptedBlocks) {
            Double blockValeu = new Double(String.valueOf(blockValue));
            dechriptedValue = (int) this.getDechriptedBlock(blockValeu.doubleValue());
            asciiMessage.append(dechriptedValue);
        }
        return asciiMessage.toString();
    }

    /**
     * @param asciiMessage is the message in the ascii format, containing only digits
     * @return the original message based on the ascii format of the message
     */
    private String getORiginalMessage(String asciiMessage) {
        StringBuilder origem = new StringBuilder();
        while (asciiMessage.length() > 0) {
            String valueStr = asciiMessage.substring(0, 3);
            asciiMessage = asciiMessage.substring(3, asciiMessage.length());
            int asciiValue = Integer.parseInt(valueStr) - 100;
            String charValue = new Character((char) asciiValue).toString();
            origem.append(charValue);
        }
        return origem.toString();
    }

    /**
     * Prints the log into the filesystem
     * 
     * @param stream is the printstream to print the message. System.out, Servlet.getOutputStream, etc.
     */
    public void printLog(PrintStream stream) {
        for (String logEntry : this.log) {
            stream.println(logEntry);
        }
    }
}
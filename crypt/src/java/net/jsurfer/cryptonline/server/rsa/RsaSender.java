/**
 * RsaSender.java
 *
 * @author Created by Marcello Junior
 */

package net.jsurfer.cryptonline.server.rsa;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class RsaSender {

    /** The original message to be sent */
    private String originalMessage;
    /** The cripted message as a result of the rsa algorithm */
    private String chriptedMessage;
    /** The rsa structure containing the public and private keys */
    private double n;
    private double e;

    private List<String> log;
    private DecimalFormat decimalFormat = new DecimalFormat("0");
    private static final Algebra algebra = Algebra
            .getInstance();

    /**
     * Constructs a new RsaSender with the originalMessage along with the Rsa
     * structure
     * 
     * @param originalMessage is the original human-readable message
     * @param rsa is the RSA object holding the public and private keys
     */
    public RsaSender(String originalMessage, Rsa rsa) {
        this.log = new ArrayList<String>();
        this.n = rsa.getPublicKey_N();
        this.e = rsa.getPublicKey_E();
        this.initializeSender(originalMessage);
    }

    public RsaSender(String originalMessage, double n, double e) {
        this.log = new ArrayList<String>();
        this.n = n;
        this.e = e;
        this.initializeSender(originalMessage);
    }

    private void initializeSender(String originalMessage) {
        this.originalMessage = originalMessage;
        this.chriptedMessage = this.enchriptMessage(originalMessage);
        this.log.add("");
        this.log.add(Rsa.LOG_ARROW + "Encrypted Message");
        this.log.add(this.chriptedMessage);
    }

    /**
     * // *
     * 
     * @return The original message
     */
    public String getOriginalMessage() {
        return this.originalMessage;
    }

    /**
     * @return the chripted message
     */
    public String getEncryptedMessage() {
        return this.chriptedMessage;
    }

    /**
     * @return The ASCII representation of the original message
     */
    public String getAsciiMessage() {
        return this.getAsciiString(this.originalMessage);
    }

    /**
     * @param originalMessage is the human-readable message
     * @return the chripted message based on the original human-readable message and the RSA private and public keys
     */
    private String enchriptMessage(String originalMessage) {
        this.log.add("Sending a message");
        this.log.add("");
        this.log.add(Rsa.LOG_ARROW + "Original Message");
        this.log.add(originalMessage);
        this.log.add("");
        this.log.add(Rsa.LOG_ARROW + "Setting the receiver's public key");
        this.log.add("(N , E) = (" + this.decimalFormat.format(this.n) + " , "
                + this.decimalFormat.format(this.e) + ")");
        this.log.add("");
        this.log.add(Rsa.LOG_ARROW + "Transforming the message to ASCII code");
        String asciiMessage = this.getAsciiString(originalMessage);
        this.log.add(asciiMessage);
        this.log.add("");
        this.log
                .add(Rsa.LOG_ARROW
                        + "Configuring randomly selected blocks from the ASCII message");
        List<Integer> asciiBlocks = this.getAsciiBlocks(asciiMessage);
        return this.getChriptedBlocks(asciiBlocks);
    }

    /** Returns the ASCII representation a string */
    private String getAsciiString(String widestring) {
        StringBuffer ascii = new StringBuffer();
        for (int i = 0; i < widestring.length(); i++) {
            ascii.append((int) widestring.charAt(i) + 100);
        }
        return ascii.toString();
    }

    /**
     * Returns the set of blocks of the ASCII representation in a random way
     * 1241355334 => [1241][355][3][34]. It can't begin with 0's.
     */
    private List<Integer> getAsciiBlocks(String asciiM) {
        int i, tam;
        String asciiMessage = asciiM;
        List<Integer> blocks = new ArrayList<Integer>();
        String stringN = new String(new Double(this.n).toString());
        int blockLength = stringN.length() - 3;

        while (asciiMessage.length() > 0) {
            tam = asciiMessage.length();

            i = algebra.getARandomInteger(blockLength); // uses n +1

            if (i > tam)
                i = tam;

            if (tam != i)
                while (asciiMessage.charAt(i) == '0')
                    i++;

            String toBeCopied = asciiMessage.substring(0, i);
            if (Integer.parseInt(toBeCopied) > this.n) { // use n
                i--;
                while (asciiMessage.charAt(i) == '0') {
                    i--;
                }
            }

            String block = asciiMessage.substring(0, i);
            asciiMessage = asciiMessage.substring(i, asciiMessage.length());
            blocks.add(new Integer(block));
        }
        return blocks;
    }

    /**
     * Enchripts a block with the Power Module N algorithm [1241] => 343434
     * 
     * @param x
     *            is the number representing a block
     * @return the result of the Power Module N algorithm
     */
    private double getChriptedBlock(int x) {
        double chriptedb = algebra.getPowerModuleN(x, this.e, this.n); // as the pair
        this.log.add("Block(" + this.decimalFormat.format(x) + ") = "
                + this.decimalFormat.format(x) + " ^ "
                + this.decimalFormat.format(this.e) + " mod "
                + this.decimalFormat.format(this.n) + " = "
                + this.decimalFormat.format(chriptedb));
        return chriptedb;
    }

    /**
     * Returns the set of chripted blocks of the ascii representation applying
     * the Power Module N algorithm Coded blocks: [1241][355][3][34] ==>
     * Encriteped Final message: 343434-43552-43544-3435
     * 
     * @param codedBlocks
     *            is the list of coded blocks to be encripted
     * @return the encripted message based on the Power Module N algorithm based
     *         on the coded blocks
     */
    private String getChriptedBlocks(List<Integer> codedBlocks) {
        this.log.add("Bloco(x) = x ^ E mod N");
        this.log.add("");

        String wrongC;
        double chriptedBlock;
        StringBuilder code = new StringBuilder();

        for (int blockValue : codedBlocks) {
            chriptedBlock = this.getChriptedBlock(blockValue);
            wrongC = chriptedBlock + ""; // take off .0 of the value
            wrongC = wrongC.substring(0, wrongC.length() - 2);
            code.append(wrongC + Rsa.DELIMITER);
        }

        String coded = code.toString();
        coded = coded.substring(0, coded.length() - 1);
        return coded;
    }

    public void printLog() {
        for (String logEntry : this.log) {
            System.out.println(logEntry);
        }
    }

    public Iterator<String> getLog() {
        return this.log.iterator();
    }
}
/**
 * RsaReceiver.java
 *
 * @author Created by Omnicore CodeGuide
 */

package net.jsurfer.cryptonline.server.rsa;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;


public class RsaReceiver {

    /** The original message received */
    private String originalMessage;
    /** The chripted message as a result of the rsa algorithm */
    private String chriptedMessage;
    /** The rsa structure containing the public and private keys */
    private Rsa rsa;

    private List<String> log;
    private DecimalFormat decimalFormat = new DecimalFormat("0");

    private static final Algebra algebra = Algebra.getInstance();

    /**
     * Constructs a new RsaSender with the originalMessage along with the Rsa structure
     */
    public RsaReceiver(String chriptedMessage, Rsa rsa) {
        this.rsa = rsa;
        this.log = new ArrayList<String>();
        this.chriptedMessage = chriptedMessage;
        this.originalMessage = this.getDeschriptedMessage(this.chriptedMessage);
        this.log.add("");
        this.log.add(Rsa.LOG_ARROW + "Original Message");
        this.log.add(this.originalMessage);
    }

    public String getOriginalMessage() {
        return this.originalMessage;
    }

    public String getEncryptedMessage() {
        return this.chriptedMessage;
    }

    private String getDeschriptedMessage(String chriptedMessage) {
        this.log.add("Receiving the message");
        this.log.add("");
        this.log.add(Rsa.LOG_ARROW + "Encrypted Message");
        this.log.add(chriptedMessage);
        this.log.add("");
        this.log.add(Rsa.LOG_ARROW + "Setting the private key");
        this.log.add("(N , D) = ("
                + this.decimalFormat.format(this.rsa.getPublicKey_N()) + " , "
                + this.decimalFormat.format(this.rsa.getPrivateKey_D()) + ")");

        List<Integer> chriptedBlocks = this.getChriptedBlocks(chriptedMessage);
        this.log.add("");
        this.log.add(Rsa.LOG_ARROW + "Decripting each block");
        String dechriptedBlocks = this
                .getDeschriptedBlocksInAscii(chriptedBlocks);
        this.log.add("");
        this.log.add(Rsa.LOG_ARROW + "Complete message in ASCII");
        this.log.add(dechriptedBlocks);
        return this.getORiginalMessage(dechriptedBlocks);
    }

    private List<Integer> getChriptedBlocks(String chriptedMessage) {
        List<Integer> chriptedBlocks = new ArrayList<Integer>();
        StringTokenizer tokenizer = new StringTokenizer(chriptedMessage,
                Rsa.DELIMITER);
        while (tokenizer.hasMoreTokens()) {
            String chriptedBlock = tokenizer.nextToken();
            chriptedBlocks.add(new Integer(chriptedBlock));
        }
        return chriptedBlocks;
    }

    private double getDechriptedBlock(double chriptedBlock) {
        double dechriptedb = algebra.getPowerModuleN(chriptedBlock, this.rsa.getPrivateKey_D(), 
                                                     this.rsa.getPublicKey_N());
        this.log.add("Ascii(" + this.decimalFormat.format(chriptedBlock)
                + ") = " + this.decimalFormat.format(chriptedBlock) + " ^ "
                + this.decimalFormat.format(this.rsa.getPrivateKey_D())
                + " mod "
                + this.decimalFormat.format(this.rsa.getPublicKey_N()) + " = "
                + this.decimalFormat.format(dechriptedb));
        return dechriptedb;
    }

    private String getDeschriptedBlocksInAscii(List<Integer> chriptedBlocks) {
        this.log.add("Ascii(x) = x ^ D mod N");
        this.log.add("");

        StringBuilder asciiMessage = new StringBuilder();
        int dechriptedValue;
        for (int blockValue : chriptedBlocks) {
            Double blockValeu = new Double(String.valueOf(blockValue));
            dechriptedValue = (int) this.getDechriptedBlock(blockValeu
                    .doubleValue());
            asciiMessage.append(dechriptedValue);
        }
        return asciiMessage.toString();
    }

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

    public void printLog() {
        for (String logEntry : this.log) {
            System.out.println(logEntry);
        }
    }

    public Iterator<String> getLog() {
        return this.log.iterator();
    }
}
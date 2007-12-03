/**
 * RsaReceiver.java
 *
 * @author Created by Omnicore CodeGuide
 */

package net.jsurfer.cryptonline.server.rsa;

import java.util.Vector;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.text.DecimalFormat;

import net.jsurfer.cryptonline.server.math.Algebra;

public class RsaReceiver{
	
	/** The original message received */
	private String originalMessage;
	/** The chripted message  as a result of the rsa algorithm */
	private String chriptedMessage;
	/** The rsa structure containing the public and private keys */
	private Rsa rsa;

	private Vector log;
	private DecimalFormat decimalFormat = new DecimalFormat("0");
	
	/** Constructs a new RsaSender with the originalMessage along with the Rsa structure*/
	public RsaReceiver(String chriptedMessage, Rsa rsa){
		this.rsa = rsa;
		this.log = new Vector();
		this.chriptedMessage = chriptedMessage;
		this.originalMessage = this.getDeschriptedMessage(this.chriptedMessage);
		this.log.add("");
		this.log.add(Rsa.SETA_LOG+"Mensagem Original");
		this.log.add(this.originalMessage);
	}
	
	public String getOriginalMessage(){
		return this.originalMessage;
	}
	
	public String getChriptedMessage(){
		return this.chriptedMessage;
	}
	
	private String getDeschriptedMessage(String chriptedMessage){
		this.log.add("Recebendo Mensagem");
		this.log.add("");
		this.log.add(Rsa.SETA_LOG+"Mensagem Criptografada");
		this.log.add(chriptedMessage);
		this.log.add("");
		this.log.add(Rsa.SETA_LOG+"Setando chave privada");
		this.log.add("(N , D) = ("+this.decimalFormat.format(this.rsa.getPublicKey_N())+" , "+this.decimalFormat.format(this.rsa.getPrivateKey_D())+")");
						
		Vector chriptedBlocks = this.getChriptedBlocks(chriptedMessage);
		this.log.add("");
		this.log.add(Rsa.SETA_LOG+"Decodificando cada bloco");
		String dechriptedBlocks = this.getDeschriptedBlocksInAscii(chriptedBlocks);
		this.log.add("");
		this.log.add(Rsa.SETA_LOG+"Mensagem completa em ASCII");
		this.log.add(dechriptedBlocks);
		return this.getORiginalMessage(dechriptedBlocks);
	}
	
	private Vector getChriptedBlocks(String chriptedMessage){
		String aux = "",blocks;
		Vector chriptedBlocks = new Vector();
		StringTokenizer tokenizer = new StringTokenizer(chriptedMessage,Rsa.DELIMITER);
		while (tokenizer.hasMoreTokens()){
			String chriptedBlock = tokenizer.nextToken();
			chriptedBlocks.add(chriptedBlock);
		}
		return chriptedBlocks;
	}
	
	private double getDechriptedBlock(double chriptedBlock){
		Algebra algebra = Algebra.getInstance();
		double dechriptedb = algebra.getPowerModuleN(chriptedBlock,this.rsa.getPrivateKey_D(),this.rsa.getPublicKey_N());
		this.log.add("Ascii("+this.decimalFormat.format(chriptedBlock)+") = "+this.decimalFormat.format(chriptedBlock)+" ^ "+this.decimalFormat.format(this.rsa.getPrivateKey_D())+" mod "+this.decimalFormat.format(this.rsa.getPublicKey_N())+" = "+this.decimalFormat.format(dechriptedb));
		return dechriptedb;
	}
	
	private String getDeschriptedBlocksInAscii(Vector chriptedBlocks){
		int dechriptedValeu;
		Iterator blocks = chriptedBlocks.iterator();
		//String asciiMessage = "";
		this.log.add("Ascii(x) = x ^ D mod N");
		this.log.add("");
		StringBuffer asciiMessage = new StringBuffer();
		while (blocks.hasNext()){
			Double blockValeu = new Double((String)blocks.next());
			dechriptedValeu = (int)this.getDechriptedBlock(blockValeu.doubleValue());
			asciiMessage.append(dechriptedValeu+"");
		}
        return asciiMessage.toString();
	}
	
	private String getORiginalMessage(String asciiMessage){
		//String origem = "";
		StringBuffer origem = new StringBuffer();
		while(asciiMessage.length() > 0){
			String valueStr = asciiMessage.substring(0,3);
			asciiMessage = asciiMessage.substring(3,asciiMessage.length());
			int asciiValue = Integer.parseInt(valueStr)-100;
			String charValue = new Character((char)asciiValue).toString();
			origem.append(charValue);
		}
		return origem.toString();
	}
	
	public void printLog(){
		Iterator it = this.log.iterator();
		while (it.hasNext()){
			String logLine = (String)it.next();
			System.out.println(logLine);
		}
	}
	
	public Iterator getLog(){
		return this.log.iterator();
	}
}


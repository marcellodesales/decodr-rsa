/**
 * RsaSender.java
 *
 * @author Created by Marcello Junior
 */

package net.jsurfer.cryptonline.server.rsa;

import java.util.Vector;
import java.util.Iterator;
import java.text.DecimalFormat;

import net.jsurfer.cryptonline.server.math.Algebra;

public class RsaSender{
	
	/** The original message to be sent */
	private String originalMessage;
	/** The chripted message as a result of the rsa algorithm */
	private String chriptedMessage;
	/** The rsa structure containing the public and private keys */
	private double n;
	private double e;
	
	private Vector log;
	private DecimalFormat decimalFormat = new DecimalFormat("0");
	
	/** Constructs a new RsaSender with the originalMessage along with the Rsa structure*/
	public RsaSender(String originalMessage, Rsa rsa){
		this.log = new Vector();
		this.n = rsa.getPublicKey_N();
		this.e = rsa.getPublicKey_E();
		this.initializeSender(originalMessage);
	}
	
	public RsaSender(String originalMessage, double n, double e){
		this.log = new Vector();
		this.n = n;
		this.e = e;
		this.initializeSender(originalMessage);
	}
	
	private void initializeSender(String originalMessage){
		this.originalMessage = originalMessage;
		this.chriptedMessage = this.getChriptedMessage(this.originalMessage);
		this.log.add("");
		this.log.add(Rsa.SETA_LOG+"Mensagem Criptografada");
		this.log.add(this.chriptedMessage);
	}
		
	/** Gets the original message */
	public String getOriginalMessage(){
		return this.originalMessage;
	}
	
	/** Gets the chripted message */
	public String getChriptedMessage(){
		return this.chriptedMessage;
	}
	
	/** Gets the ascii representation of the original message */
	public String getAsciiMessage(){
		return this.getAsciiString(this.originalMessage);
	}
	
	/** Returns the chripted message */
	private String getChriptedMessage(String originalMessage){
		this.log.add("Enviando Mensagem");
		this.log.add("");
		this.log.add(Rsa.SETA_LOG+"Mensagem Original");
		this.log.add(originalMessage);
		this.log.add("");
		this.log.add(Rsa.SETA_LOG+"Setando chave p�blica do destinat�rio");
		this.log.add("(N , E) = ("+this.decimalFormat.format(this.n)+" , "+this.decimalFormat.format(this.e)+")");
		this.log.add("");
		this.log.add(Rsa.SETA_LOG+"Transformando a mensagem para o c�digo ASCII");
		String asciiMessage = this.getAsciiString(originalMessage);
		this.log.add(asciiMessage);
		this.log.add("");
		this.log.add(Rsa.SETA_LOG+"Codificando blocos aleat�rios");
		Vector asciiBlocks = this.getAsciiBlocks(asciiMessage);
		//this.log.add(Rsa.SETA_LOG+"Pegar a criptografia dos blocos");
		return this.getChriptedBlocks(asciiBlocks);
	}
	
	/** Returns the ascii representation a string */
	private String getAsciiString(String widestring){
		StringBuffer ascii = new StringBuffer();
		for (int i=0; i < widestring.length(); i++){
			ascii.append((int)widestring.charAt(i)+100);
		}
		return ascii.toString();
	}
	
	/**
	 * Returns the set of blocks of the ascii representation in a random way
	 * 1241355334 => [1241][355][3][34]. It can't begin with 0's.
	 */
	private Vector getAsciiBlocks(String asciiM){
		Algebra algebra = Algebra.getInstance();
  		int i,tam;;
		String asciiMessage = asciiM;
		Vector blocks = new Vector();
		String stringN = new String(new Double(this.n).toString());
		int blockLength = stringN.length()-3;
		
		newstring:
    	while (asciiMessage.length() > 0){
			tam = asciiMessage.length();
			
			i = algebra.getRandom(blockLength); //utiliza n +1

			if (i > tam) i = tam;
			
			if (tam != i)
				while (asciiMessage.charAt(i) == '0') i++;
			
			String toBeCopied = asciiMessage.substring(0,i);
			if (Integer.parseInt(toBeCopied) > this.n){ //utiliza n
            	i--;
            	while (asciiMessage.charAt(i) == '0') i--;
			}
			
			String block = asciiMessage.substring(0,i);
			
			asciiMessage = asciiMessage.substring(i,asciiMessage.length());
			blocks.add(new Integer(block));
		}
		return blocks;
	}

	/**
	 * Enchripts a block with the Power Module N algorithm
	 * [1241] => 343434
	 */
	private double getChriptedBlock(int x){
		Algebra algebra = Algebra.getInstance();
		double chriptedb = algebra.getPowerModuleN(x,this.e,this.n); //e passado como par
		this.log.add("Bloco("+this.decimalFormat.format(x)+") = "+this.decimalFormat.format(x)+" ^ "+this.decimalFormat.format(this.e)+" mod "+this.decimalFormat.format(this.n)+" = "+this.decimalFormat.format(chriptedb));
		return chriptedb;
	}
	
	/**
	 * Returns the set of chripted blocks of the ascii representation applying
	 * the Power Module N algorithm
	 * [1241][355][3][34] => 343434-43552-43544-3435
	 *
	 */
	private String getChriptedBlocks(Vector codedBlocks){
		String wrongC;
		StringBuffer code = new StringBuffer();
		double chriptedBlock;
        Iterator blocks = codedBlocks.iterator();
		this.log.add("Bloco(x) = x ^ E mod N");
		this.log.add("");
        while (blocks.hasNext()){
			//code = code + 'C('+inttostr(codedBlocks[i])+') ';
			Integer blockValue = (Integer)blocks.next();
			chriptedBlock = this.getChriptedBlock(blockValue.intValue());
			wrongC = chriptedBlock+""; //take off .0 of the value
			wrongC = wrongC.substring(0,wrongC.length()-2);
            code.append(wrongC + Rsa.DELIMITER);
		}
		String coded = code.toString();
		coded = coded.substring(0,coded.length()-1);
		return coded;
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


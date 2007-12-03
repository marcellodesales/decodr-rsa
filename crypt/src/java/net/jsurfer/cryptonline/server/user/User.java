/**
 * User.java
 *
 * @author Created by Marcello Junior
 */

package net.jsurfer.cryptonline.server.user;

import java.text.DecimalFormat;

import net.jsurfer.cryptonline.server.rsa.Rsa;

public class User{
	
	private Rsa rsa;
	private String id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	
	//private Locale local;
	private String publicKeyN;
	private String publicKeyE;
	private String privateKeyD;
			
	public User(){		
		this.rsa = new Rsa();
		this.setRsaValuesOnCreation();
	}
	
	/**
	 * setRsaValuesOnCreation() used in the creation of the
	 * User constructor, in order to set key property's values.
	 */
	private void setRsaValuesOnCreation(){
		DecimalFormat formater = new DecimalFormat("0");;
		this.publicKeyN =  String.valueOf(formater.format(this.rsa.getPublicKey_N()));
		this.publicKeyE =  String.valueOf(formater.format(this.rsa.getPublicKey_E()));
		this.privateKeyD =  String.valueOf(formater.format(this.rsa.getPrivateKey_D())); 		
	}
		
	public void setRsa(Rsa rsa) {
		this.rsa = rsa;
		this.setRsaValuesOnCreation();
	}
	
	public Rsa getRsa() {
		return rsa;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return this.password;
	}

	/**
	 * @return
	 */
	public String getId() {
		return this.id;
	}
	
	/**
	 * @param string
	 */
	public void setId(String id) {
		this.id = id;
	}	
	
	public String getName(){
		return this.firstName + " " + this.lastName;
	}

	public String getPublicKeyN(){
		DecimalFormat formater = new DecimalFormat("0");;
		return String.valueOf(formater.format(this.rsa.getPublicKey_N()));
	}
	
	public String getPublicKeyE(){
		DecimalFormat formater = new DecimalFormat("0");;
		return String.valueOf(formater.format(this.rsa.getPublicKey_E()));
	}
	
	public String getPrivateKeyD(){
		DecimalFormat formater = new DecimalFormat("0");;
		return String.valueOf(formater.format(this.rsa.getPrivateKey_D()));
	}
			
	public void setPublicKeyN(String publicKeyN) {
		this.publicKeyN = publicKeyN;
		this.rsa.setPublicKey_N(Double.parseDouble(this.publicKeyN));
	}
			
	public void setPublicKeyE(String publicKeyE) {
		this.publicKeyE = publicKeyE;
		this.rsa.setPublicKey_E(Double.parseDouble(this.publicKeyE));
	}
		
	public void setPrivateKeyD(String privateKeyD) {
		this.privateKeyD = privateKeyD;
		this.rsa.setPrivateKey_D(Double.parseDouble(this.privateKeyD));
	}
	
	public void changeRSA(){
		this.setRsa(new Rsa());
	}
		
	public void printAll(){
		System.out.println(this.getId());
		System.out.print(this.getFirstName());
		System.out.println(" "+this.getLastName());
		System.out.println(this.getEmail());
		System.out.println(this.getPassword());
		System.out.println(this.getPublicKeyN());
		System.out.println(this.getPublicKeyE());
		System.out.println(this.getPrivateKeyD());
	}
	
	/**
	 *
	 */
	public static void main(String[] args){
		try{
			User user = new User();
			user.setFirstName("Marcello");
			user.setLastName("Junior");
			user.setEmail("marcellojunior@hotmail.com");
			user.setPassword("minhaSenha@2k");
			user.printAll();
			
//			User user = (User)DefaultPersistence.getInstance().find(User.ATTRIBUTE_ID,new Integer(262144),User.class);
//			user.printAll();
			//Rsa rsa = new Rsa(171307,3,113067);
//			String origem = "Voc�s gostariam de um prato de banana machucada?";
			//RsaSender sender = new RsaSender(origem,user.getRsa());
//			sender.printLog();
	//		System.out.println("Cripted: "+sender.getChriptedMessage());
			//RsaReceiver receiver = new RsaReceiver("86855-125-1-112621-27-104867-166732-70041-29085-2197-104867-74413-62309-27-1520-136260-115599-106243-8000-14930-29791-56138",rsa);
			//System.out.println("Original: "+receiver.getOriginalMessage()+"");
			
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}

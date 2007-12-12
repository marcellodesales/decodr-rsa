///**
// * @author Marcello Junior on 29/11/2003
// * Cryptography on the Web
// * This is the main fa�ade of the system. It's the sama time a Fa�ade and
// * a Singleton Design Pattern.
// */
//
//package net.jsurfer.cryptonline.server.rsa;
//
//import java.text.DecimalFormat;
//import java.util.Hashtable;
//import java.util.Locale;
//
//import net.jsurfer.cryptonline.util.http.SendEmail;
//import net.jsurfer.cryptonline.util.persistence.PersistenceLayerException;
//import net.jsurfer.cryptonline.util.persistence.PersistenceBrokerFactory;
//import net.jsurfer.cryptonline.server.user.User;
//
///**
// * @author Marcello Junior on 29/11/2003
// * Cryptography on the Web
// * This is the main fa�ade of the system. It's the sama time a Fa�ade and
// * a Singleton Design Pattern.
// */
//public class RsaFacade{
//	
//	private final String SIGNIN_ADDRES = "http://www.mycgiserver.com/~cryptonline/signin.jsp?";
//	private Locale defaultLocale = new Locale("EN","US");
//	
//	private static RsaFacade senderUseCase = null;
//	private DecimalFormat decimalFormat = new DecimalFormat("0");
//	
//	private RsaFacade(){
//	}
//	
//	/**
//	 *  This method must be called to create an instance of this class.
//	 * @return RsaFacade single instance.
//	 */
//	public static RsaFacade getInstance(){
//		if (RsaFacade.senderUseCase == null){
//			RsaFacade.senderUseCase = new RsaFacade();
//		}
//		return RsaFacade.senderUseCase;
//	}
//	
//	/**
//	 * Method sendMessage. Manda uma mensagem e retorna o log do envio...
//	 *
//	 * @param    user                an User
//	 * @param    originalMessage     a  String
//	 * @param    email               a  String
//	 *
//	 * @return   an Iterator
//	 *
//	 */
//	public RsaSender sendMessage(User user, String originalMessage, String email, String name){
//		User userEmail = null;
//		try{
//			userEmail = (User)PersistenceBrokerFactory.getInstance().getPersistenceLayer().find("email",email,User.class);
//		} catch (PersistenceLayerException e) {
//			e.printStackTrace();
//		}
//		RsaSender rsaSender = null;
//		String information = "";
//		if (userEmail == null){
//			Rsa rsa = new Rsa();
//			rsaSender = new RsaSender(originalMessage,rsa);
//			information = "Caro "+name+", \n\nVoc� recebeu uma mensagem criptografada com o algoritmo RSA de "+user.getName()+".";
//			information += "\n\n"+rsaSender.getChriptedMessage()+"\n\n";
//			information += "Para descriptografar sua mensagem, d� um clique no CriptOnline! http://www.graw.tci.ufal.br/marcellojunior/criptonline/";
//			information += " e acompanhe todo o algoritmo utilizado para as opera��es de criptografica.\nSe cadastre utilando as chaves seguintes:";
//	
//			information += "\n\nN = "+decimalFormat.format(rsa.getPublicKey_N())+"\nE = "+decimalFormat.format(rsa.getPublicKey_E())+"\nD = "+decimalFormat.format(rsa.getPrivateKey_D());
//			
//			information += "\n\nclicando em "+SIGNIN_ADDRES+"n="+decimalFormat.format(rsa.getPublicKey_N())+"&e="+decimalFormat.format(rsa.getPublicKey_E())+"&d="+decimalFormat.format(rsa.getPrivateKey_D())+"&email="+email;
//			
//			information += "\n\nPara responder a mensagem de "+user.getFirstName()+" utilize a chave p�blica dele:";
//			information += "\n\nN = "+decimalFormat.format(user.getRsa().getPublicKey_N())+"\nE = "+decimalFormat.format(user.getRsa().getPublicKey_E())+"\n\n";
//			information += "Maiores informa��es sobre o algor�tmo RSA, acesse http://www.graw.tci.ufal.br/marcellojunior/criptonline/\n";
//			information += "Marcello Junior - Ci�ncia da Computa��o / UFAL - marcellojunior@hotmail.com";
//		} else {
//			rsaSender = new RsaSender(originalMessage,user.getRsa());
//			information = "Caro "+name+", \n\nVoc� recebeu uma mensagem criptografada com o algoritmo RSA de "+user.getName()+".";
//			information += "\n\n"+rsaSender.getChriptedMessage()+"\n\n";
//			information += "Para descriptografar sua mensagem, d� um clique no CriptOnline! http://www.graw.tci.ufal.br/marcellojunior/criptonline/";
//			information += " , fa�a o login e clique em Receber Mensagem.";
//			information += "\n\nPara responder a mensagem de "+user.getFirstName()+" utilize a chave p�blica dele:";
//			information += "\n\nN = "+decimalFormat.format(user.getRsa().getPublicKey_N())+"\nE = "+decimalFormat.format(user.getRsa().getPublicKey_E())+"\n\n";
//			information += "Maiores informa��es sobre o algor�tmo RSA, acesse http://www.graw.tci.ufal.br/marcellojunior/criptonline/\n";
//			information += "Marcello Junior - Ci�ncia da Computa��o / UFAL - marcellojunior@hotmail.com";
//		}
//		try{
//			SendEmail.getInstance().send("cryptonline@mycgiserver.com","CryptOnline Service",email,"You received a crypted message!",information);
//		} catch (Exception e){
//			e.printStackTrace();
//		}
//		return rsaSender;
//	}
//	
//	/**
//	 * The User receives an encrypted message. This creates a RsaReceiver 
//	 * with all the information about the just receive message.
//	 * @param user: The user who received the encrypted message.
//	 * @param encryptedMessage: The encrypted message sent to the user.
//	 * @return RsaReceiver with all the information about the message.
//	 */
//	public RsaReceiver receiveMessage(User user, String encryptedMessage){
//		return new RsaReceiver(encryptedMessage,user.getRsa());
//	}
//	
//	/**
//	 * Makes the Login of a user. It can be using a browser or a web service.
//	 * @param email: The email of the user.
//	 * @param password: the password of the user.
//	 * @return a User. If the user is not found, returns null.
//	 */
//	public User doLogin(String email, String password){
//		User user = null;
//		try{
//			Hashtable loginInfo = new Hashtable();
//			loginInfo.put("email",email);
//			loginInfo.put("password",password);
//			user = (User)PersistenceBrokerFactory.getInstance().getPersistenceLayer().find(loginInfo,User.class);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return user;
//	}
//	
//	/**
//	 * Creates a new User. It automaticaly creates a RSA object to him. 
//	 * @param firstName: The first name of the user.
//	 * @param lastName: The last name of the user.
//	 * @param email: The email of the user.
//	 * @param password: The password of the user
//	 * @return a new User with all the parameters.
//	 * @throws PersistenceLayerException: If a user already exits.
//	 */
//	public User createNewUser(String firstName, String lastName, String email, String password) throws PersistenceLayerException{
//		return this.createNewUser(firstName,lastName,email,password,null);
//	}
//
//	/**
//	 * Create a new User with a specified Rsa Object.
//	 * @param firstName: The first name of the user.
//	 * @param lastName: The last name of the user.
//	 * @param email: The email of the user.
//	 * @param password: The password of the user
//	 * @return a new User with all the parameters.
//	 * @throws PersistenceLayerException: If a user already exits.
//	 */
//	public User createNewUser(String firstName, String lastName, String email, String password, Rsa rsa) throws PersistenceLayerException{
//
//		User user = (User)PersistenceBrokerFactory.getInstance().getPersistenceLayer().find("email",email,User.class);
//		if (user == null){
//			user = new User();
//			user.setFirstName(firstName);
//			user.setLastName(lastName);
//			user.setEmail(email);
//			user.setPassword(password);
//			if (rsa != null) user.setRsa(rsa);
//			PersistenceBrokerFactory.getInstance().getPersistenceLayer().saveObject(user);
//			return user;
//		} else 
//		throw new PersistenceLayerException("exception.general.userexists");
//	}
//	
//	/**
//	 * Removes a user from the Rsa System. 
//	 * @param user: an existing user of the system.
//	 * @return a boolean indicating if the user was removed.
//	 * @throws PersistenceLayerException
//	 */
//	public boolean removeUser(User user) throws PersistenceLayerException{
//		try {
//			return PersistenceBrokerFactory.getInstance().getPersistenceLayer().deleteObject(user);	
//		} catch (Exception e) {
//			return false;
//		}		
//	}
//	
//	/**
//	 * Makes the Login of a user. It can be using a browser or a web service.
//	 * @param email: The email of the user.
//	 * @param password: the password of the user.
//	 * @return a User. If the user is not found, returns null.
//	 */
//	public boolean removeUser(String email){
//		User user = null;
//		try{
//			Hashtable loginInfo = new Hashtable();
//			loginInfo.put("email",email);
//			user = (User)PersistenceBrokerFactory.getInstance().getPersistenceLayer().find(loginInfo,User.class);
//			return this.removeUser(user);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return false;
//	}
//}
//

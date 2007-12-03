/**
 * Sendmail.java
 *
 * @author Aula web development group
 */

package net.jsurfer.cryptonline.util.http;

import java.util.Properties;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Collection;
import java.util.Vector;
import java.util.Iterator;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeBodyPart;
import javax.activation.DataSource;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;

public class SendEmail{
	
	/** Inst�ncia �nica de SendEmail. (Singleton) */
	private static SendEmail singletonInstance = null;
	
	private final String EMAIL_SERVER_HOST    = "smtp.mcs2.netarray.com";
	private final String EMAIL_SERVER_PORT    = "25";
	private final String ADMIN_EMAIL          = "cryptonline@mycgiserver.com";
	private final String DEFAULT_CONTENT_TYPE = "text/plain";
	
	private static String[] signature = {
		"                                                        ",
		"                                                        ",
		"                                      M\"\"MMM\"\"MMM\"\"M   ",
		"                                       M  MMM  MMM  M   ",
		"            .d8888b. 88d888b. .d8888b. M  MMP  MMP  M   ",
		"            88'  `88 88'  `88 88'  `88 M  MM'  MM' .M   ",
		"            88.  .88 88       88.  .88 M  `' . '' .MM   ",
		"            `8888P88 dP       `88888P8 M    .d  .dMMM   ",
		"                 .88                   MMMMMMMMMMMMMM   ",
		"             d8888P   Comunidades de gradua��o na Web   ",
		"                                                        ",
		"          	    Ci�ncia da Computa��o                    ",
		"          Departamento de Tecnologia da Informa��o (TCI)",
		"              Universidade Federal de Alagoas (UFAL)    ",
		"                          Alagoas - Brasil              ",
		"                     Contato: graw@tci.ufal.br          ",
		"                                                        ",
		"                                                        "
	};

	private String getContentsWithSignature(String messageContents){
		String[] signature = SendEmail.signature;
		StringBuffer newSignature = new StringBuffer(messageContents);
		for (int i = 0; i < signature.length; i++){
			newSignature.append("\n"+signature[i]);
		}
		return newSignature.toString();
	}
	
	//Singleton para sendmail
	private SendEmail(){}
	
	/**
	 * Method getInstance
	 *
	 * @return   Um singleton dessa classe.
	 *
	 */
	public static SendEmail getInstance(){
		if (singletonInstance == null){
			singletonInstance = new SendEmail();
		}
		return singletonInstance;
	}
	
	/**
	 * Method getSessionInstance
	 *
	 * @return   Uma Session com o servidor de envio de email padr�o.
	 *
	 */
	private Session getSessionInstance(){
		// Get system properties
		Properties props = System.getProperties();
		// Setup mail server
		props.put("mail.smtp.host", this.EMAIL_SERVER_HOST);
		props.put("mail.smtp.port", this.EMAIL_SERVER_PORT);
		
		/// Get session
		return Session.getDefaultInstance(props, null);
	}
	
	/**
	 * Method send
	 *
	 * @param    fromAddress         Endere�o de email de quem est� enviando email.
	 * @param    fromName            O nome de quem est� enviando email.
	 * @param    toAddress           Endere�o de email de quem receber� o email.
	 * @param    subject             O t�tulo do email.
	 * @param    messageContents     Todo conte�do do email.
	 *
	 * @throws   Exception
	 *
	 */
	public void send(String fromAddress, String fromName, String toAddress,
							String subject, String messageContents)
		throws Exception {

		if (fromName == null) fromName="";
		
		String[] email = new String[1];
		email[0] = toAddress;
		// Define message
		//MimeMessage message = new MimeMessage(this.getSessionInstance());
		MimeMessage message = this.getNewMessage(fromAddress,fromName,email,subject,messageContents,null);
			
		// Send message just one we use this method.. see the difference on those with array of addresses
		this.transportMessage(message);
	}
	
	/**
	 * Method send. Enviar email para multiplos destinos.
	 *
	 * @param    fromAddress         Endere�o de email de quem est� enviando email.
	 * @param    fromName            O nome de quem est� enviando email.
	 * @param    toAddress[]         O conjunto de endere�os de email de quem receber� o email.
	 * @param    subject             O t�tulo do email.
	 * @param    messageContents     Todo conte�do do email.
	 *
	 * @throws   Exception
	 *
	 */
	public void send(String fromAddress, String fromName, String toAddress[],
							 String subject, String messageContents) throws Exception {
		this.send(fromAddress,fromName,toAddress,subject,messageContents,null);
	}
	
	/**
	 * Method send. Para enviar email para multiplos endere�os com arquivo atachado.
	 *
	 * @param    fromAddress         Endere�o de email de quem est� enviando email.
	 * @param    fromName            O nome de quem est� enviando email.
	 * @param    toAddress[]         O conjunto de endere�os de email de quem receber� o email.
	 * @param    subject             O t�tulo do email.
	 * @param    messageContents     Todo conte�do do email.
	 * @param    files               Conjunto de arquivos para attachment
	 *
	 * @throws   Exception
	 *
	 */
	private void send(String fromAddress, String fromName, String toAddress[],
							 String subject, String messageContents, String attachFiles[]) throws Exception {
		try{
			Hashtable emailCollection = this.getDomainEmailsCollection(toAddress);
			Enumeration domains = emailCollection.keys();
			while(domains.hasMoreElements()){
				String domain = (String)domains.nextElement();
				Object[] allSafeToEmails = ((Collection)emailCollection.get(domain)).toArray();
				
				if (fromName==null) fromName="";
							
				MimeMessage message = this.getNewMessage(fromAddress,fromName,allSafeToEmails,subject,messageContents,attachFiles);
				
				// Send message
				this.transportMessage(message);
			}
		}catch(Exception e ){
			e.printStackTrace();
		}
	}
	
	private MimeMessage getNewMessage(String fromAddress, String fromName, Object[] allSafeToEmails,
							 String subject, String messageContents, String files[]) throws Exception{
		
		//messageContents = this.getContentsWithSignature(messageContents);
		MimeMessage message = new MimeMessage(this.getSessionInstance());
		try{
			message.setFrom(new InternetAddress(fromAddress,fromName));
			InternetAddress toInternetAddresses[] = new InternetAddress[allSafeToEmails.length];
			//Prepare the ToAddress. Note that the first element was taken to be the toAddress
			//on the recipient part
			InternetAddress toAddressEmail = new InternetAddress((String)allSafeToEmails[0]);
			message.addRecipient(Message.RecipientType.TO,toAddressEmail);
			
			//If there is more than 2 messages in the collection
			if (toInternetAddresses.length > 1){
				for (int i=1 ; i< toInternetAddresses.length ; i++){
					message.addRecipients(Message.RecipientType.BCC,(String)allSafeToEmails[i]);
				}
			}
			
			message.setSubject(subject);

			if (files != null){
				BodyPart messageBodyPart = new MimeBodyPart();
				//message.setText(messageContents);ddddddddddddddd
				messageBodyPart.setContent(messageContents,DEFAULT_CONTENT_TYPE);
				// Put parts in message
				message.setContent(this.uploadAttatches(messageBodyPart,files));
			} else message.setContent(messageContents,DEFAULT_CONTENT_TYPE);
			
			message.setSentDate(new java.util.Date());
			
		}catch (Exception e){
			e.printStackTrace();
		}
		return message;
	}
	
	/**
	 * Method transportMessage. Envia a mensagem para os destinat�rios atrav�s do servidor web.
	 * http://developer.java.sun.com/developer/onlineTraining/JavaMail/contents.html#JavaMailTransport
	 * @param    message             A mensagem pronta.
	 *
	 */
	private void transportMessage(MimeMessage message){
		//http://developer.java.sun.com/developer/onlineTraining/JavaMail/contents.html#JavaMailTransport 26/06/2002
		try{
			if (message.getMessageNumber() > 1){
				Transport transport = this.getSessionInstance().getTransport();
				transport.connect();
				transport.sendMessage(message,message.getAllRecipients());
				transport.close();
				
			} else Transport.send(message);
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Method uploadAttatches. Atacha ao corpo do email arquivos.
	 *
	 * @param    bodyPart            O corpo da mensagem, como descri��o, mime-type...
	 * @param    files               O conjunto de caminhos de arquivos a serem atachados ao corpo do email.
	 *
	 * @return   a Multipart A ser inserido na mensagem.
	 *
	 */
	private Multipart uploadAttatches(BodyPart bodyPart,String files[]) throws Exception{
		/* Cuida do attachment */
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(bodyPart);
		DataSource source;
		
		/* Adiciona todos os arquivos */
		for (int i=0 ; i < files.length ; i++ ){
			source = new FileDataSource(files[i]);
			bodyPart = new MimeBodyPart();
			bodyPart.setDataHandler(new DataHandler(source));
			bodyPart.setFileName(source.getName());
			multipart.addBodyPart(bodyPart);
		}
		return multipart;
	}
	
	/**
	 * Method getDistinguishedCollection. Retorna todos os emails de uma cole��o
	 * indexada de emails de um determinado dom�nio.
	 *
	 * @param    domain              � um dom�nio espec�fico existente na cole��o indexada.
	 * @param    indexedEmailCollectiona  Cole��o indexada que cont�m o dom�nio.
	 *
	 * @return   a String[] todos os emails de uma cole��o.
	 *
	 */
	private String[] getDistinguishedEmailCollection(String domain, Hashtable indexedEmailCollection){
		Collection emails = (Collection)indexedEmailCollection.get(domain);
		Iterator allEmails = emails.iterator();
		String[] domainEmails = new String[emails.size()];
		int i = 0;
		while (allEmails.hasNext()){
			domainEmails[i] = (String)allEmails.next();
		}
		return domainEmails;
	}
	
	/**
	 * Method putElementOnIndexedCollection. Coloca um elemento numa cole��o indexada.
	 * Neste caso o email em seu correto conjunto indexado por seu dom�nio.
	 *
	 * @param    indexedCollection   O conjunto indexado com key=dominio e value=Collection de strings.
	 * @param    email               O novo email a ser colocado na cole��o indexada.
	 *
	 * @return   A cole��o indexada atualizada com o novo elemento.
	 *
	 */
	private Hashtable putElementOnIndexedCollection(Hashtable indexedCollection, String email){
		int indexOfAt = email.indexOf("@");
		String domain = email.substring(indexOfAt + 1, email.length());
		
		//If the set of domains already contains the domain as a key
		if (indexedCollection.containsKey(domain)){
			Collection emailCollection = (Collection)indexedCollection.get(domain);
			if (!emailCollection.contains(email)){
				emailCollection.add(email);
				indexedCollection.put(domain,emailCollection);
			}
		} else {
			Collection emailCollection = new Vector();
			emailCollection.add(email);
			indexedCollection.put(domain,emailCollection);
		}
		return indexedCollection;
	}
	
	/**
	 * Method getDomainEmailsCollection. Recupera um conjunto de emails ordenados
	 * por seus respectivos dom�nios. Chave: dominio value: Collection de Strings dos emails.
	 *
	 * @param    emails 		Um conjunto de emails num array de strings
	 *
	 * @return   a Hashtable    O conjunto de cole��es de emails ordenados por dom�nios.
	 * <b>Key</b> = dom�nio tci.ufal.br | <b>value</b> = Collection de Strings dos emails masj@tci.ufal.br.
	 *
	 */
	private Hashtable getDomainEmailsCollection(String[] emails){
		Hashtable setOfDomain = new Hashtable();//Key=domain | value=emailCollection
		String email = "";
		for (int i = 0; i < emails.length; i++){
			email = emails[i];
			setOfDomain = this.putElementOnIndexedCollection(setOfDomain,email);
		}
		return setOfDomain;
	}
	
	/**
	 * Method getDomainEmailsCollection. Recupera um conjunto de emails ordenados
	 * por seus respectivos dom�nios. Chave: dominio value: Collection de Strings dos emails.
	 *
	 * @param    emails 		Um conjunto de emails numa collection.
	 *
	 * @return   a Hashtable    O conjunto de cole��es de emails ordenados por dom�nios.
	 * <b>Key</b> = dom�nio tci.ufal.br | <b>value</b> = Collection de Strings dos emails masj@tci.ufal.br.
	 *
	 */
	private Hashtable getDomainEmailsCollection(Collection emails){
		Hashtable setOfDomain = new Hashtable();//Key=domain | value=emailCollection
		Iterator emailsCollection = emails.iterator();
		String email = "";
		while(emailsCollection.hasNext()){
			email = (String)emailsCollection.next();
			setOfDomain = this.putElementOnIndexedCollection(setOfDomain,email);
		}
		return setOfDomain;
	}
	
	/**
	 * This method is called after start up.
	 *
	 * @param args   The command-line arguments.
	 *               They can be changed in the
	 *               "Project Settings" dialog in CodeGuide.
	 */
	public static void main(String[] args){
		System.out.println("Mail");
		try{
			SendEmail sendemail = SendEmail.getInstance();
			
			String[] emails = {"javaman@moomia.com"};
			//String[] attach = {"C:/tunes.txt"};
			
			sendemail.send(sendemail.EMAIL_SERVER_HOST,
							"Marcello Junior",emails,
							"Criptografia RSA Online Teste",
							"Caros usu�rios,\n Estou testando a nova API\n\nObrigado!");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("Finished! Press ENTER to continue.");
	}
}


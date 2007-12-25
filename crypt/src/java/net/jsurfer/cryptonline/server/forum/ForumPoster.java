/**
 * User.java
 *
 * @author Created by Marcello Junior
 */

package net.jsurfer.cryptonline.server.forum;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

import net.jsurfer.cryptonline.server.rsa.Rsa;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.SessionFactory;
import net.sf.hibernate.cfg.Configuration;

public class ForumPoster{
	
	private Rsa rsa;
	private String id;
	private String name;
	private String email;
	private String password;
				
	public ForumPoster(){
	    this.id = System.currentTimeMillis() + "";
		this.rsa = new Rsa();
//		this.setRsaValuesOnCreation();
	}
	
//	/**
//	 * setRsaValuesOnCreation() used in the creation of the
//	 * User constructor, in order to set key property's values.
//	 */
//	private void setRsaValuesOnCreation(){
//		DecimalFormat formater = new DecimalFormat("0");;
//	}
		
	public void setRsa(Rsa rsa) {
		this.rsa = rsa;
//		this.setRsaValuesOnCreation();
	}
	
	public Rsa getRsa() {
		return rsa;
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

	public String getKeyN(){
		return String.valueOf(new DecimalFormat("0").format(this.rsa.getPublicKey_N()));
	}
	
	public String getKeyE(){
		return String.valueOf(new DecimalFormat("0").format(this.rsa.getPublicKey_E()));
	}
	
	public String getKeyD(){
		return String.valueOf(new DecimalFormat("0").format(this.rsa.getPrivateKey_D()));
	}
			
	public void setKeyN(String publicKeyN) {
	    publicKeyN = publicKeyN == null ? "0.0" : publicKeyN;
	    try {
	        this.rsa.setPublicKey_N(new Double(publicKeyN));
	    } catch (Exception e){
	        this.rsa.setPublicKey_N(new Double(0));
	    }
	        
	}
			
	public void setKeyE(String publicKeyE) {
	    publicKeyE = publicKeyE == null ? "0.0" : publicKeyE;
        try {

            this.rsa.setPublicKey_E(new Double(publicKeyE));
        } catch (Exception e){
            this.rsa.setPublicKey_N(new Double(0));
        }
	}
		
	public void setKeyD(String privateKeyD) {
	    privateKeyD = privateKeyD == null ? "0.0" : privateKeyD;
	    try {
	        this.rsa.setPrivateKey_D(new Double(privateKeyD));
	    } catch (Exception e){
	        this.rsa.setPublicKey_N(new Double(0));
	    }
	}
	
	public void changeRSA(){
		this.setRsa(new Rsa());
	}
		
	public void printAll(){
	    Calendar cal = GregorianCalendar.getInstance();
	    cal.setTimeInMillis(Long.parseLong(this.getId()));
	    System.out.println(DateFormat.getInstance().format(cal.getTime()));
		System.out.println(this.getId());
		System.out.print(this.getName());
		System.out.println(this.getEmail());
		System.out.println(this.getPassword());
		System.out.println(this.getKeyN());
		System.out.println(this.getKeyE());
		System.out.println(this.getKeyD());
		this.rsa.printAll();
	}
	
	/**
     * @return
     */
    public String getName() {
        return this.name;
    }
    
    public void setName(String newname) {
        this.name = newname;
    }
    
    public static void shutdownDatabase() {

    }

    /**
	 *
	 */
	public static void main(String[] args){
		try{
			ForumPoster user = new ForumPoster();
			user.setName("Marcello Junior");
			user.setEmail("marcellojunior@hotmail.com");
			user.setPassword("minhaSenha@2k");
			//user.printAll();
			
			Session session = null;

			    try{

			        // This step will read hibernate.cfg.xml and prepare hibernate for use
			        SessionFactory sessionFactory = new Configuration().configure("/hibernate.cfg.xml").buildSessionFactory();			        
			        session =sessionFactory.openSession();
//			        session.setFlushMode(FlushMode.AUTO)
			         //Transaction tr = session.beginTransaction();
			         
//			         System.out.println("before saving");
			         session.save(user);
			         session.flush();
//			         System.out.println("after saving");
			         //tr.commit();
			         
			            //Using from Clause
			            String SQL_QUERY ="from Poster poster";
			            Query query = session.createQuery(SQL_QUERY);
			            for(Iterator it=query.iterate();it.hasNext();){
			                ForumPoster poster=(ForumPoster)it.next();
			                poster.printAll();
			            }
			         System.out.println("done...");
		        }catch(Exception e){
		            e.printStackTrace();
		            
		        }finally{
		            session.close();
		        }
			
			//			Poster user = (Poster)DefaultPersistence.getInstance().find(User.Poster,new Integer(262144),Poster.class);
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

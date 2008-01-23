/**
 * User.java
 *
 * @author Created by Marcello Junior
 */

package net.jsurfer.cryptonline.server.forum;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import net.jsurfer.cryptonline.util.persistence.PersistenceBrokerFactory;
import net.jsurfer.cryptonline.util.persistence.PersistenceLayer;
import net.sf.hibernate.Session;
import net.sf.hibernate.SessionFactory;
import net.sf.hibernate.cfg.Configuration;

public final class ForumPoster{

	private String oid;

	private String name;

	private String email;

	private String password;

	private String primeP;

	private String primeQ;

	public ForumPoster(){
	    this.oid = System.currentTimeMillis() + "";
	}

	public ForumPoster(String name, String email, String password) {
	    this();
	    this.name = name;
	    this.email = email;
	    this.password = password;
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
	 * @return the OID of the poster
	 */
	public String getOid() {
		return this.oid;
	}

	/**
	 * @param string
	 */
	public void setOid(String id) {
 	    this.oid = id;
	}

	/**
     * @return the primeP
     */
    public String getPrimeP() {
        return primeP;
    }

    /**
     * @param primeP the primeP to set
     */
    public void setPrimeP(String primeP) {
        this.primeP = primeP;
    }

    /**
     * @return the primeQ
     */
    public String getPrimeQ() {
        return primeQ;
    }

    /**
     * @param primeQ the primeQ to set
     */
    public void setPrimeQ(String primeQ) {
        this.primeQ = primeQ;
    }

    @Override
    public String toString() {
    	return "[Poster-" + this.oid + "] " + this.name + " (" + this.primeP + "," + this.primeQ + ") <" + this.email + ">";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || !(obj instanceof ForumPoster)) {
            return false;
        }
        ForumPoster forumObj = (ForumPoster)obj;

        // if the id is missing, return false

        if (forumObj.oid == null || forumObj.oid.equals("")) return false;

        // equivalence by id
        return this.oid.equals(forumObj.oid);
    }

    @Override
    public int hashCode() {
        if (oid != null) {
            return oid.hashCode();
        } else {
            return super.hashCode();
        }
    }

    public void printAll(){
	    Calendar cal = GregorianCalendar.getInstance();
	    cal.setTimeInMillis(Long.parseLong(this.getOid()));
	    System.out.println(DateFormat.getInstance().format(cal.getTime()));
		System.out.println(this.getOid());
		System.out.print(this.getName());
		System.out.println(this.getEmail());
		System.out.println(this.getPassword());
		System.out.println(this.getPrimeP());
		System.out.println(this.getPrimeQ());
	}

    /**
	 *
	 */
	public static void main(String[] args){
		try{
			ForumPoster user = new ForumPoster("Marcello Junior", "s22d@dd.com", "minhaSenha@2k");
			user.setPrimeP("33");
			user.setPrimeQ("33453");;
			//user.printAll();

			Session session = null;

			    try{

			        // This step will read hibernate.cfg.xml and prepare hibernate for use
			        SessionFactory sessionFactory = new Configuration().configure("/hibernate.cfg.xml").buildSessionFactory();
			        session =sessionFactory.openSession();
//			        session.setFlushMode(FlushMode.AUTO)
			         //Transaction tr = session.beginTransaction();

//			         System.out.println("before saving");
//			         session.saveOrUpdate(user);
//			         session.flush();
//			         System.out.println("after saving");
			         //tr.commit();

			            //Using from Clause
//			            String SQL_QUERY ="from Poster poster";
//			            Query query = session.createQuery(SQL_QUERY);
//			            for(Iterator it=query.iterate();it.hasNext();){
//			                ForumPoster poster=(ForumPoster)it.next();
//			                poster.setEmail("anewemail@gmail.com");
//			                poster.printAll();
//			                  session.saveOrUpdate(user);
//			                     session.flush();
//			            }
			         PersistenceLayer pl = PersistenceBrokerFactory.getInstance().getPersistenceLayer();
			         ForumPoster user2 = (ForumPoster)pl.find("email", user.getEmail(), ForumPoster.class);
			         user2.setEmail("different@updated.com");
			         session.update(user2);
			         session.flush();

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

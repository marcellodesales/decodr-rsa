/**
 * 
 */
package net.jsurfer.cryptonline.server.forum;

import java.util.Iterator;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.SessionFactory;
import net.sf.hibernate.cfg.Configuration;

/**
 * @author marcello
 *
 */
public class ForumThread {
    
    private String oid;
    
    private String subject;
    
    private ForumPoster creator;

    public ForumThread() {
        this.oid = System.currentTimeMillis() + "";
    }
    
    public ForumThread(String subject) {
        this();
        this.subject = subject;
    }
    
    /**
     * @return the oid
     */
    public String getOid() {
        return oid;
    }

    /**
     * @param oid the oid to set
     */
    public void setOid(String oid) {
        this.oid = oid;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the creator
     */
    public ForumPoster getCreator() {
        return creator;
    }

    /**
     * @param creator the creator to set
     */
    public void setCreator(ForumPoster creator) {
        this.creator = creator;
        
    }
    
    public static void main(String[] args) {
        
        Session session = null;
         try {
             SessionFactory sessionFactory = new Configuration().configure("/hibernate.cfg.xml").buildSessionFactory();
             session =sessionFactory.openSession();
             
             Query q = session.createQuery("from Poster where email='marcellojunior@hotmail.com'");
             System.out.println(q.getQueryString());
             ForumThread newThread = new ForumThread("Check this out!");

             for(Iterator it=q.iterate();it.hasNext();){
                 ForumPoster poster=(ForumPoster)it.next();

                 newThread.setCreator(poster);
                 newThread.printAll();
             }
             
             session.save(newThread);
             session.flush();
             
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                session.close();
            } catch (HibernateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 
     */
    public void printAll() {
        System.out.println("Thread -----> " + this.getSubject());
        System.out.println("Creator:");
        this.creator.printAll();
    }

}

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
public class ForumMessage {

    private String oid;
    private String title;
    private String text;
    private ForumThread thread;
    private ForumPoster poster;

    /**
     *
     */
    public ForumMessage() {
        this.oid = System.currentTimeMillis() + "";
    }

    /**
     * @param thread2
     * @param poster2
     * @param title2
     * @param text2
     */
    public ForumMessage(ForumThread thread, ForumPoster poster, String title, String text) {
        this();
        this.thread = thread;
        this.poster = poster;
        this.title = title;
        this.text = text;
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
     * @return the thread
     */
    public ForumThread getThread() {
        return thread;
    }

    /**
     * @param thread the thread to set
     */
    public void setThread(ForumThread thread) {
        this.thread = thread;
    }
    /**
     * @return the poster
     */
    public ForumPoster getPoster() {
        return poster;
    }
    /**
     * @param poster the poster to set
     */
    public void setPoster(ForumPoster poster) {
        this.poster = poster;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }
    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the header
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public void printAll() {
        //this.thread.printAll();
        System.out.println("Message Information");
        System.out.println(this.title);
        System.out.println(this.text);
    }

    public static void main(String[] args) {

        Session session = null;
         try {
             SessionFactory sessionFactory = new Configuration().configure("/hibernate.cfg.xml").buildSessionFactory();
             session =sessionFactory.openSession();

             Query q = session.createQuery("from ForumThread where poster_id='1197454553878'");
             System.out.println(q.getQueryString());

             ForumThread thread = null;
             ForumMessage message = new ForumMessage();
             for(Iterator<ForumThread> it = q.iterate();it.hasNext();){
                 thread = it.next();
                 System.out.println(thread);

                 message.setText("Now it is working");
                 message.setTitle("All about secret messages...");
                 message.setPoster(thread.getCreator());
                 message.setThread(thread);

                 session.save(message);
                 session.flush();

                 break;
             }


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
}

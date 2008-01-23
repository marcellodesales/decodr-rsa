/**
 *
 */
package net.jsurfer.cryptonline.server.forum;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.SessionFactory;
import net.sf.hibernate.cfg.Configuration;

/**
 * @author marcello
 */
public final class ForumThread {

    private String oid;

    private String subject;

    private ForumPoster creator;

    private Set<ForumMessage> messages = new HashSet<ForumMessage>();

    public ForumThread() {
        this.oid = System.currentTimeMillis() + "";
    }

    public ForumThread(String subject) {
        this();
        this.subject = subject;
    }

    public void addMessage(ForumMessage message) {
        this.messages.add(message);
    }

    public Set<ForumMessage> getMessages() {
        return this.messages;
    }

    public void setMessages(Set<ForumMessage> messages) {
        this.messages = messages;
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

    @Override
    public String toString() {
        return "[ForumThread-" + this.oid + "] Messages#(" + this.messages.size() + ") " + this.subject + " by ("
                + this.getCreator() + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || !(obj instanceof ForumThread)) {
            return false;
        }
        ForumThread threadObj = (ForumThread) obj;

        // if the id is missing, return false
        if (threadObj.oid == null || threadObj.oid.equals(""))
            return false;

        // equivalence by id
        return this.oid.equals(threadObj.oid);
    }

    @Override
    public int hashCode() {
        if (oid != null) {
            return oid.hashCode();
        } else {
            return super.hashCode();
        }
    }

    public static void main(String[] args) {

        Session session = null;
        try {
            SessionFactory sessionFactory = new Configuration().configure("/hibernate.cfg.xml").buildSessionFactory();
            session = sessionFactory.openSession();

            Query q = session.createQuery("from Poster where email='marcellojunior@hotmail.com'");
            System.out.println(q.getQueryString());
            ForumThread newThread = new ForumThread("Check this out!");

            for (Iterator it = q.iterate(); it.hasNext();) {
                ForumPoster poster = (ForumPoster) it.next();

                newThread.setCreator(poster);
            }

            session.save(newThread);
            session.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                session.close();
            } catch (HibernateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
}

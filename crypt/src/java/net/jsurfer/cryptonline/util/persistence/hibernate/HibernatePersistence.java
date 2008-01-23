package net.jsurfer.cryptonline.util.persistence.hibernate;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.MappingException;
import net.sf.hibernate.Session;
import net.sf.hibernate.SessionFactory;
import net.sf.hibernate.cfg.Configuration;

/**
 * Criado em 20/11/2003
 *
 * @author Marcello Junior
 */
public class HibernatePersistence {

	/** unique instance */
	private static HibernatePersistence sInstance = new HibernatePersistence();

	/** Session Factory to create sessions. */
	private SessionFactory sessions = null;
	private Session session;

	/**
	 * Private constructor. Only called by the getInstanceMelhod.
	 */
	private HibernatePersistence() {
		super();
	}

	/**
	 * Get the unique instance of this class.
	 */
	public static synchronized HibernatePersistence getInstance() {
		return sInstance;
	}

	/**
	 * Creates a SessionFactory of Hinernate. It's the primary method to
	 * start hibernate with the descrition files.
	 * @return a SessionFactory instance of hibernate.
	 * @throws HibernateException
	 * @throws MappingException
	 */
	public SessionFactory getSessionFactory() throws HibernateException, MappingException{
		//TODO Create a Property file with all mappings.
		//and use the addFile method to put them there.
		//configuration catches the mappings defs.
		//hibernate.hbm.xml
		Configuration cfg = new Configuration();
		cfg.configure("/hibernate.cfg.xml");
		//cfg.addFile("./src/br/ufal/cryptonline/User.hbm.xml"); this also works
		//cfg.addFile("./Forum.hbm.xml");

		this.sessions = cfg.buildSessionFactory();
		return this.sessions;
	}

	/**
	 * A main session object instance is retrieve from this method
	 * in order to persist, retrieve, delete and update objects in
	 * a persistence layer.
	 * @see net.sf.hibernate.Session
	 * @return a Session reference.
	 * @throws HibernateException
	 */
	public Session getSession() throws HibernateException {
		if (this.sessions == null) {
			this.getSessionFactory();
		}
		if (this.session == null) {
			this.session = this.sessions.openSession();
		}
		return this.session;
	}
}
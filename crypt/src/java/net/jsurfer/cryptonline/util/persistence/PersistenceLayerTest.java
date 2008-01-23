package net.jsurfer.cryptonline.util.persistence;

import net.jsurfer.cryptonline.server.forum.ForumMessage;
import net.jsurfer.cryptonline.server.forum.ForumThread;
import junit.framework.TestCase;

/**
 * @author Marcello Junior
 * 20/11/2003
 */
public class PersistenceLayerTest extends TestCase {

	private PersistenceLayer pl;

	/* (n�o-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp(){
		this.pl = PersistenceBrokerFactory.getInstance().getPersistenceLayer();
		this.createTransientUser();
	}

	/* (n�o-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown(){

	}

	private void createTransientUser(){

	}

	public void testSaveObject() {
		try {
			ForumMessage message = (ForumMessage)this.pl.find("email","marcellojunior@hotmail.com",ForumMessage.class);
			message.printAll();

		} catch (Exception ple) {
			ple.printStackTrace();
		}
	}

	public void testFind() {
		this.createTransientUser();
	}
}
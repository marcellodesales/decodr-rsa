/*
 * Criado em 20/11/2003
 *
 * Marcello Junior Marcello Junior
 * javaman@moomia.com
 */
package net.jsurfer.cryptonline.persistence;

import net.jsurfer.cryptonline.server.user.User;
import junit.framework.TestCase;

/**
 * @author Marcello Junior
 *
 * Projeto desenvolvido em J2EE
 */
public class PersistenceLayerTest extends TestCase {

	private User user;
	private PersistenceLayer pl;
	
	/* (n�o-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp(){
		this.pl = PersistenceBrokerFactory.getInstance().getPersistenceLayer();
		this.user = new User();
		this.user.setFirstName("firstName");
		this.user.setLastName("lastName");
		this.user.setEmail("email");
		this.user.setPassword("password");				
	}
	
	/* (n�o-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown(){
		 
	}
	
	private void createTransientUser(){
		this.pl = PersistenceBrokerFactory.getInstance().getPersistenceLayer();
		this.user = new User();
		this.user.setFirstName("firstName");
		this.user.setLastName("lastName");
		this.user.setEmail("email");
		this.user.setPassword("password");		
	}

	public void testSaveObject() {
		try {			
			pl.saveObject(this.user);
			User saved = (User)this.pl.find("email","email",User.class);
			
			assertEquals(this.user.getId(),saved.getId());
			assertEquals(this.user.getFirstName(),saved.getFirstName());
			assertEquals(this.user.getLastName(),saved.getLastName());
			assertEquals(this.user.getEmail(),saved.getEmail());
			assertEquals(this.user.getPassword(),saved.getPassword());			
			assertEquals(this.user.getPrivateKeyD(),saved.getPrivateKeyD());
			assertEquals(this.user.getPublicKeyE(),saved.getPublicKeyE());
			assertEquals(this.user.getPublicKeyN(),saved.getPublicKeyN());
			
			pl.deleteObject(this.user);
							
		} catch (Exception ple) {
			ple.printStackTrace();			
		}
	}
	
	public void testFind() {
		this.createTransientUser();
	}
}

package net.jsurfer.cryptonline.server.rsa;
///*
// * Created on 28/11/2003
// * Cryptography on the Web
// */
//package net.jsurfer.cryptonline.server;
//
////import net.jsurfer.cryptonline.persistence.PersistenceBrokerFactory;
////import net.jsurfer.cryptonline.persistence.PersistenceLayer;
////import net.jsurfer.cryptonline.persistence.PersistenceLayerException;
//import net.jsurfer.cryptonline.server.rsa.RsaFacade;
//import net.jsurfer.cryptonline.server.user.User;
//import junit.framework.TestCase;
//
///**
// * @author Marcello Junior on 28/11/2003
// * Cryptography on the Web
// */
//public class RsaFacadeTest extends TestCase {
//
//	private User user;
////	private PersistenceLayer pl;
//	
//	/**
//	 * Constructor for RsaFacadeTest.
//	 * @param arg0
//	 */
//	public RsaFacadeTest(String arg0) {
//		super(arg0);
//	}
//
////	protected void setUp(){
////		this.pl = PersistenceBrokerFactory.getInstance().getPersistenceLayer();
////	}
////
////	public void testAccessTime(){
////		long startTime = System.currentTimeMillis();
////		PersistenceBrokerFactory.getInstance();
////		long endTime = System.currentTimeMillis();
////		System.out.println("Time taken for getting Objects at Last: "+ (endTime - startTime) );
////		assertTrue((endTime-startTime)<100);		
////	}
////	
////	public void testCreateNewUser() {		
////		try {
////			System.out.println("criar");
////			RsaFacade.getInstance().createNewUser("firstName","lastName","email","password");
////			//User danielle = (User)this.pl.find("email","daniellehouly@hotmail.com",User.class);
////			//RsaFacade.getInstance().removeUser(danielle);
////			//RsaFacade.getInstance().removeUser("email");
////						
////		} catch (PersistenceLayerException ple) {			
////			System.out.println(ple.getMessage());
////			try {
////					
////			} catch (Exception f) {
////				f.printStackTrace();
////			}
////			
////		}
////	}
//}

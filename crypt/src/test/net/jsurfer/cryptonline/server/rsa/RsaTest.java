/**
 * 
 */
package net.jsurfer.cryptonline.server.rsa;

import net.jsurfer.cryptonline.server.rsa.Rsa;
import net.jsurfer.cryptonline.server.rsa.RsaFactory;
import junit.framework.TestCase;

/**
 * @author marcello
 *
 */
public class RsaTest extends TestCase {

    public void testRsaCreation() {
        Rsa a = RsaFactory.getInstance().createRsa();
        assertNotNull(a);
        assertNotNull(a.getLog());
        assertTrue(a.getDInverseE() > 0.0);
        assertTrue(a.getPrivateKey_D() > 0.0);
        assertTrue(a.getPublicKey_E() > 0.0);
        assertTrue(a.getPublicKey_N() > 0.0);
    }
    
    public void testRsaCreationWithPrimes() {
        Rsa a = RsaFactory.getInstance().createRsa(11027, 739);
        assertNotNull(a);
        assertNotNull(a.getLog());
        assertTrue(a.getPublicKey_N() == (double)8148953);
        assertTrue(a.getPublicKey_E() == (double)5);
        assertTrue(a.getPrivateKey_D() == (double)4882313);
    }
}

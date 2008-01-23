package net.jsurfer.cryptonline.server.rsa;

import net.jsurfer.cryptonline.server.rsa.Algebra;
import net.jsurfer.cryptonline.server.rsa.Rsa;
import junit.framework.TestCase;

public class AlgebraTest extends TestCase {

    private Algebra algebra = Algebra.getInstance();

    public void testGetRandomPrime() {
        
        try {
            algebra.getRandomPrime(-45);
        } catch (Exception e) {
            assertTrue("It must be illegal to use it if the input is negative", e instanceof IllegalArgumentException);
        }
        assertTrue("The prime must be equals 2", algebra.getRandomPrime(2) == 2);
        assertTrue("The prime must be equals 1", algebra.getRandomPrime(1) == 1);
        assertTrue("The prime must be bigger than the range", 
                (algebra.getRandomPrime(55) >= 1) && (algebra.getRandomPrime(55) <= 55));
        
        assertTrue("The prime must be equals 2", algebra.getRandomPrime(15000) <=15000);
        System.out.println(algebra.getRandomPrime(15000));
        System.out.println(algebra.getRandomPrime(15000));
    }
    
    public void testGetNModuleE() {
        Rsa a = new Rsa();
        
        double block = (double)336;
        System.out.println("Given number for block: "+block);
        System.out.println("N: " + a.getPublicKey_N());
        System.out.println("E: " + a.getPublicKey_E());
        System.out.println("D: " + a.getPrivateKey_D());
        
        double codedValue = algebra.getPowerModuleN(block, a.getPublicKey_E(), a.getPublicKey_N());
        double decodedValue = algebra.getPowerModuleN(codedValue, a.getPrivateKey_D(), a.getPublicKey_N());
        
        System.out.println("coded: " + codedValue);
        System.out.println("decoded: " + decodedValue);
        
        assertEquals("Reverse Function not working!", block, decodedValue);
        
    }

}

/**
 * 
 */
package net.jsurfer.cryptonline.server.rsa;

/**
 * @author marcello
 *
 */
public final class RsaFactory {

    private static final RsaFactory singleton = new RsaFactory();
    
    private RsaFactory() {
        
    }
    
    public static RsaFactory getInstance() {
        return singleton;
    }
    
    public Rsa createRsa() {
        Rsa rsa = new Rsa();
        rsa.changeKeys();
        return rsa;
    }
    
    public Rsa createRsa(int p, int q) {
        return new Rsa(p, q);
    }
    
    public RsaSender createSender(Rsa rsa, String originalMessage) {
        return new RsaSender(originalMessage, rsa);
    }
    
    public RsaReceiver createReceiver(Rsa rsa, String encryptedMessage) {
        return new RsaReceiver(encryptedMessage, rsa);
    }
}

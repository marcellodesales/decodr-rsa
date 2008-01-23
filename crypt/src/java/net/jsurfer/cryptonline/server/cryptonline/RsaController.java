/**
 * 
 */
package net.jsurfer.cryptonline.server.cryptonline;

import net.jsurfer.cryptonline.server.rsa.Rsa;
import net.jsurfer.cryptonline.server.rsa.RsaFactory;
import net.jsurfer.cryptonline.server.rsa.RsaReceiver;
import net.jsurfer.cryptonline.server.rsa.RsaSender;

/**
 * @author marcello
 *
 */
public final class RsaController {
    
    private static final RsaController singleton = new RsaController();
    
    private RsaController() {
    }
    
    public static RsaController getInstance() {
        return singleton;
    }
    
    public RsaSender createRsaSender(Rsa rsa, String originalMessage) {
        return RsaFactory.getInstance().createSender(rsa, originalMessage);
    }
        
    /**
     * The User receives an encrypted message. This creates a RsaReceiver 
     * with all the information about the just receive message.
     * @param user: The user who received the encrypted message.
     * @param encryptedMessage: The encrypted message sent to the user.
     * @return RsaReceiver with all the information about the message.
     */
    public RsaReceiver createRsaReceiver(Rsa rsa, String encryptedMessage){
        return new RsaReceiver(encryptedMessage, rsa);
    }

    /**
     * @return
     */
    public Rsa createRsa(int p, int q) {
        return RsaFactory.getInstance().createRsa(p, q);
    }
    
    public Rsa createRsa() {
        return RsaFactory.getInstance().createRsa();
    }
}

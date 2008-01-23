/**
 *
 */
package net.jsurfer.cryptonline.server.cryptonline;

import java.text.DecimalFormat;

import net.jsurfer.cryptonline.server.forum.ForumMessage;
import net.jsurfer.cryptonline.server.forum.ForumPoster;
import net.jsurfer.cryptonline.server.forum.ForumThread;
import net.jsurfer.cryptonline.server.rsa.Rsa;
import net.jsurfer.cryptonline.server.rsa.RsaFactory;
import net.jsurfer.cryptonline.server.rsa.RsaReceiver;
import net.jsurfer.cryptonline.server.rsa.RsaSender;
import net.jsurfer.cryptonline.util.persistence.PersistenceBrokerFactory;
import net.jsurfer.cryptonline.util.persistence.PersistenceLayerException;

/**
 * @author marcello
 */
public final class CryponlineFacade {

    private static final CryponlineFacade singleton = new CryponlineFacade();

    private static final DecimalFormat decimalFormat = new DecimalFormat("0");

    private CryponlineFacade() {

    }

    public static CryponlineFacade getInstance() {
        return singleton;
    }

    public ForumPoster login(String email, String password) throws PersistenceLayerException {
        return WebsiteController.getInstance().doLogin(email, password);
    }

    public ForumPoster createPoster(String name, String email, String password) throws PersistenceLayerException {
        Rsa rsa = RsaController.getInstance().createRsa();
        return ForumController.getInstance().createPoster(name, email, password, Integer.valueOf(decimalFormat.format(rsa.getP())), Integer.valueOf(decimalFormat.format(rsa.getQ())));
    }

    public ForumThread createThread(ForumPoster poster, String subject) throws PersistenceLayerException {
        return ForumController.getInstance().createThread(poster, subject);
    }

    public ForumMessage createMessage(ForumThread thread, ForumPoster poster, String title, String text)
            throws PersistenceLayerException {
        return ForumController.getInstance().createMessage(thread, poster, title, text);
    }

    public RsaSender createRsaSender(ForumMessage message) throws PersistenceLayerException {
        Rsa rsa = this.loadRsa(message.getPoster());
        return RsaController.getInstance().createRsaSender(rsa, message.getText());
    }

    public RsaReceiver createRsaReceiver(ForumMessage message) throws PersistenceLayerException {
        Rsa rsa = this.loadRsa(message.getPoster());
        RsaSender sender = this.createRsaSender(message);
        return RsaController.getInstance().createRsaReceiver(rsa, sender.getEncryptedMessage());
    }

    public Rsa createRsa(ForumPoster poster) throws PersistenceLayerException {
        Rsa rsa = RsaFactory.getInstance().createRsa();
        poster = WebsiteController.getInstance().updatePosterKeys(poster, rsa);
        PersistenceBrokerFactory.getInstance().getPersistenceLayer().saveObject(poster);
        return rsa;
    }

    public Rsa loadRsa(ForumPoster poster) {
        return RsaController.getInstance().createRsa(Integer.parseInt(poster.getPrimeP()), Integer.parseInt(poster.getPrimeQ()));
    }

    public String getEncryptedMessage(ForumMessage message) throws PersistenceLayerException {
        return this.createRsaSender(message).getEncryptedMessage();
    }

    public String getDecryptedMessage(ForumMessage message) throws PersistenceLayerException {
        return this.createRsaReceiver(message).getOriginalMessage();
    }

    public static void main(String[] args) {
        try {
            ForumPoster poster = CryponlineFacade.getInstance().createPoster("Leandro", "leandroal@gmail.com", "2345");
            poster.printAll();
            // ForumThread thread =
            // CryponlineFacade.getInstance().createThread(poster, "Welcome
            // here!");
            // ForumMessage message =
            // CryponlineFacade.getInstance().createMessage(thread, poster,
            // "This is important", "This is just " +
            // "expected for more than 4 years...");
            //
            // System.out.println(message.getTitle());
            // System.out.println(CryponlineFacade.getInstance().getEncryptedMessage(message));
            // System.out.println(CryponlineFacade.getInstance().getDecryptedMessage(message));

        } catch (PersistenceLayerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

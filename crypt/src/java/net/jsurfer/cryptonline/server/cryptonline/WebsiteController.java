/**
 *
 */
package net.jsurfer.cryptonline.server.cryptonline;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Hashtable;
import java.util.Map;

import javax.mail.MessagingException;

import net.jsurfer.cryptonline.server.forum.ForumMessage;
import net.jsurfer.cryptonline.server.forum.ForumPoster;
import net.jsurfer.cryptonline.server.forum.ForumThread;
import net.jsurfer.cryptonline.server.rsa.Rsa;
import net.jsurfer.cryptonline.server.rsa.RsaSender;
import net.jsurfer.cryptonline.util.persistence.PersistenceBrokerFactory;
import net.jsurfer.cryptonline.util.persistence.PersistenceLayerException;

/**
 * @author Marcello de Sales
 *
 */
public final class WebsiteController {

    public static final WebsiteController singleton = new WebsiteController();

    private static final DecimalFormat decimalFormat = new DecimalFormat("0");

    private WebsiteController() {

    }

    public static WebsiteController getInstance() {
        return singleton;
    }

    public ForumPoster updatePosterKeys(ForumPoster poster, Rsa rsa) throws PersistenceLayerException {
        poster.setPrimeP(String.valueOf(decimalFormat.format(rsa.getP())));
        poster.setPrimeQ(String.valueOf(decimalFormat.format(rsa.getQ())));
        PersistenceBrokerFactory.getInstance().getPersistenceLayer().saveObject(poster);
        return poster;
    }

    /**
     * Makes the Login of a user. It can be using a browser or a web service.
     * @param email: The email of the user.
     * @param password: the password of the user.
     * @return a User. If the user is not found, returns null.
     * @throws PersistenceLayerException
     */
    public ForumPoster doLogin(String email, String password) throws PersistenceLayerException{
        ForumPoster user = null;

        Map<String, String> loginInfo = new Hashtable<String, String>();
        loginInfo.put("email", email);
        loginInfo.put("password", password);
        user = (ForumPoster)PersistenceBrokerFactory.getInstance().
                                                 getPersistenceLayer().find(loginInfo, ForumPoster.class);
        return user;
    }

    private RsaSender sendMessage(ForumThread thread, ForumPoster sender, ForumMessage message, String email, String name)
            throws UnsupportedEncodingException, MessagingException, PersistenceLayerException {

        RsaSender rsaSender = this.constructRsaSender(sender, message);
        //StringBuffer encryptedMessage = constructEmailMessage(sender, message, name, rsaSender);
//        SendEmail.getInstance().send("cryptonline@mycgiserver.com", "CryptOnline Service", email,
//                                           "You received a crypted message!", encryptedMessage.toString());
        return rsaSender;
    }

    /**
     * @param sender
     * @param message
     * @param name
     * @param rsaSender
     * @return
     */
    private StringBuffer constructEmailMessage(ForumPoster sender, ForumMessage message, String name,
                                                  RsaSender rsaSender) {
        StringBuffer encryptedMessage = new StringBuffer();
        encryptedMessage.append("Dear , ");
        encryptedMessage.append(name);
        encryptedMessage.append("\n\nYou're receiving a different message today from your friend ");
        encryptedMessage.append(sender.getName());
        encryptedMessage.append(". It is based on a mathematical algorithm called RSA and it is a secret for you!\n\n");
        encryptedMessage.append("Subject: '");
        encryptedMessage.append(message.getThread().getSubject());
        encryptedMessage.append("'\n\nTitle:\n");
        encryptedMessage.append(message.getTitle());
        encryptedMessage.append("'\n\nMessage:\n");
        encryptedMessage.append(rsaSender.getEncryptedMessage());
        encryptedMessage.append("\n\nIn order to read yout message, check it online with the code '");
        encryptedMessage.append(message.getOid());
        encryptedMessage.append("'. You can see all the steps to generate the message.");
        return encryptedMessage;
    }

    private RsaSender constructRsaSender(ForumPoster sender, ForumMessage message) {
        RsaSender rsaSender = null;
        Rsa rsa = RsaController.getInstance().createRsa(Integer.parseInt(sender.getPrimeP()),
                                                        Integer.parseInt(sender.getPrimeQ()));
        rsaSender = new RsaSender(message.getText(), rsa);
        return rsaSender;
    }

    public void recoverForgottenLogin(String email) {

    }
}
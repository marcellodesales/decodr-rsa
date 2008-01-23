/**
 *
 */
package net.jsurfer.cryptonline.server.cryptonline;

import net.jsurfer.cryptonline.server.forum.ForumFactory;
import net.jsurfer.cryptonline.server.forum.ForumMessage;
import net.jsurfer.cryptonline.server.forum.ForumPoster;
import net.jsurfer.cryptonline.server.forum.ForumThread;
import net.jsurfer.cryptonline.server.rsa.Rsa;
import net.jsurfer.cryptonline.util.persistence.PersistenceBrokerFactory;
import net.jsurfer.cryptonline.util.persistence.PersistenceLayerException;

/**
 * @author marcello
 */
public final class ForumController {

    private static final ForumController singleton = new ForumController();

    private ForumController() {
    }

    public static ForumController getInstance() {
        return singleton;
    }

    public ForumPoster loadPoster(String posterId) throws PersistenceLayerException {
        return (ForumPoster) PersistenceBrokerFactory.getInstance().getPersistenceLayer().find(posterId, ForumPoster.class);
    }

    public ForumPoster loadPosterByEmail(String email) throws PersistenceLayerException {
        return (ForumPoster) PersistenceBrokerFactory.getInstance().getPersistenceLayer().find("email", email, ForumPoster.class);
    }

    public ForumThread loadThread(String threadId) throws PersistenceLayerException {
        return (ForumThread) PersistenceBrokerFactory.getInstance().getPersistenceLayer().find(threadId, ForumThread.class);
    }

    public ForumMessage loadMessage(String messageId) throws PersistenceLayerException {
        return (ForumMessage) PersistenceBrokerFactory.getInstance().getPersistenceLayer().find(messageId, ForumMessage.class);
    }

    /**
     * Create a new User with a specified Rsa Object.
     * @param firstName The first name of the user.
     * @param lastName The last name of the user.
     * @param email The email of the user.
     * @param password The password of the user
     * @return a new User with all the parameters.
     * @throws PersistenceLayerException: If a user already exits.
     */
    public ForumPoster createPoster(String name, String email, String password, Integer primeP, Integer primeQ) throws PersistenceLayerException {

        ForumPoster user = (ForumPoster)PersistenceBrokerFactory.getInstance().
                                                       getPersistenceLayer().find("email", email,ForumPoster.class);
        if (user == null){
            user = ForumFactory.getInstance().createPoster(name, email, password, primeP, primeQ);

            PersistenceBrokerFactory.getInstance().getPersistenceLayer().saveObject(user);
            return user;
        } else {
            throw new PersistenceLayerException("There's already a user with the given email " + email);
        }
    }

    public ForumThread createThread(ForumPoster sender, String subject) throws PersistenceLayerException {
        ForumThread thread = ForumFactory.getInstance().createThread(sender, subject);
        PersistenceBrokerFactory.getInstance().getPersistenceLayer().saveObject(thread);
        return thread;
    }

    public ForumMessage createMessage(ForumThread thread, ForumPoster poster, String title, String text)
            throws PersistenceLayerException {
        ForumMessage message = ForumFactory.getInstance().createMessage(thread, poster, title, text);
        PersistenceBrokerFactory.getInstance().getPersistenceLayer().saveObject(message);
        return message;
    }

    public void savePoster(ForumPoster poster) throws PersistenceLayerException {
        PersistenceBrokerFactory.getInstance().getPersistenceLayer().updateObject(poster);
    }

    public void deletePoster(ForumPoster poster) throws PersistenceLayerException {
        PersistenceBrokerFactory.getInstance().getPersistenceLayer().deleteObject(poster);

    }

    public void deleteThread(ForumThread thread) throws PersistenceLayerException {
        PersistenceBrokerFactory.getInstance().getPersistenceLayer().deleteObject(thread);
    }

    public ForumThread loadThreadBySubject(String subject) throws PersistenceLayerException {
        return (ForumThread) PersistenceBrokerFactory.getInstance().getPersistenceLayer().find("subject",
                                                                                               subject,
                                                                                               ForumThread.class);
    }

    public void saveThread(ForumThread thread) throws PersistenceLayerException {
        PersistenceBrokerFactory.getInstance().getPersistenceLayer().updateObject(thread);
    }
}
package net.jsurfer.cryptonline.server.forum;


/**
 * @author marcello
 *
 */
public final class ForumFactory {

    private static final ForumFactory singleton = new ForumFactory();

    public static ForumFactory getInstance() {
        return singleton;
    }

    public ForumPoster createPoster(String name, String email, String password, Integer primeP, Integer primeQ) {
        ForumPoster sender = new ForumPoster(name, email, password);
        sender.setPrimeP(primeP.toString());
        sender.setPrimeQ(primeQ.toString());
        return sender;
    }

    public ForumThread createThread(ForumPoster poster, String subject) {
        ForumThread thread = new ForumThread(subject);
        thread.setCreator(poster);
        return thread;
    }

    public ForumMessage createMessage(ForumThread thread, ForumPoster poster, String title, String text) {
        return new ForumMessage(thread, poster, title, text);
    }
}
/**
 *
 */
package net.jsurfer.cryptonline.server.forum;

import junit.framework.TestCase;
import net.jsurfer.cryptonline.server.cryptonline.ForumController;
import net.jsurfer.cryptonline.util.persistence.PersistenceLayerException;

/**
 * @author Marcello de Sales
 */
public class ForumThreadServiceTest extends TestCase {

    private ForumController controller;

    private ForumPoster poster;

    private ForumThread thread;

    private final String DEFAULT_SUBJECT = "TEST: What is Encription?";

    private final String DEFAULT_POSTER_NAME = "Marcello TEST";

    private final String DEFAULT_POSTER_EMAIL = "marcello.sales@gmail.TEST.com";

    private final String DEFAULT_POSTER_PASSWORD = "utntest";

    public ForumThreadServiceTest() {
        this.controller = ForumController.getInstance();
    }

    /*
     * (non-Javadoc)
     *
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        System.out.println("##########> Setting up the test...");

        ForumPoster oldPoster = this.controller.loadPosterByEmail(DEFAULT_POSTER_EMAIL);
        if (!(oldPoster != null && oldPoster.getEmail() != null && DEFAULT_POSTER_EMAIL.equals(oldPoster.getEmail()))) {
            this.poster =
                    this.controller.createPoster(DEFAULT_POSTER_NAME, DEFAULT_POSTER_EMAIL, DEFAULT_POSTER_PASSWORD, 2, 3);
        } else {
            this.poster = oldPoster;
        }

        ForumThread oldThread = this.controller.loadThreadBySubject(DEFAULT_SUBJECT);
        if (oldThread != null && DEFAULT_SUBJECT.equals(oldThread.getSubject())) {
            this.thread = oldThread;
        } else {
            this.thread = this.controller.createThread(this.poster, DEFAULT_SUBJECT);

        }
    }

    /*
     * (non-Javadoc)
     *
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        System.out.println("##########> Tearing down the test...");

        // CONSTRAINT: WE CAN ONLY DELETE A POSTER AFTER WE DELETE ALL THE
        // THREADS
        if (this.thread != null) {
            ForumThread oldThread = this.controller.loadThreadBySubject(DEFAULT_SUBJECT);
            if (oldThread != null) {
                this.controller.deleteThread(this.thread);
            }
            this.thread = null;
        }

        if (this.poster != null) {
            ForumPoster oldPoster = this.controller.loadPosterByEmail(DEFAULT_POSTER_EMAIL);
            if (oldPoster != null && oldPoster.getEmail() != null && this.poster.equals(oldPoster)) {
                this.controller.deletePoster(oldPoster);
            }
            this.poster = null;
        }
    }

    public void testCreateThread() {
        System.out.println("########################### TEST CASE CREATE ##################################");
        assertEquals("The subject of the thread is is incorrect", DEFAULT_SUBJECT, thread.getSubject());
        assertEquals("The creator of the subject is incorrect", poster.getOid(), thread.getCreator().getOid());
        assertEquals("The creator is not the same object", poster, thread.getCreator());
    }

    public void testDeletionOfThread() {
        System.out.println("########################### TEST CASE DELETE ##################################");
        try {
            this.controller.deleteThread(this.thread);
            ForumThread thread = this.controller.loadThread(this.thread.getOid());
            assertNull("The poster was not properly deleted from the database", thread);
            this.thread = null;

        } catch (PersistenceLayerException e) {
            assertNotNull("The object can't be found since it was deleted", e);
        }
    }

    public void testUpdatePoster() {
        System.out.println("########################### TEST CASE UPDATE ##################################");
        try {
            String CHANGED_SUBJECT = "SUBJECT WAS CHANGED";
            this.thread.setSubject(CHANGED_SUBJECT);
            ForumController.getInstance().saveThread(this.thread);
            ForumThread changedThread = ForumController.getInstance().loadThreadBySubject(CHANGED_SUBJECT);
            assertNotNull("The poster was not correctly loaded by email", changedThread);
            assertEquals("The poster's email was not correctly changed", CHANGED_SUBJECT, changedThread.getSubject());

            this.thread.setSubject(DEFAULT_SUBJECT);
            ForumController.getInstance().saveThread(this.thread);

        } catch (PersistenceLayerException e) {
            assertNull(e);
        }
    }

    // public void testEqualityOfPosters() {
    // System.out.println("########################### TEST CASE EQUALITY
    // ##################################");
    // try {
    // ForumPoster sameDefaultPoster =
    // this.controller.loadPosterByEmail(this.poster.getEmail());
    // assertNotNull("Same poster not loaded", sameDefaultPoster);
    //
    // assertNotNull("Same poster did not load the property email",
    // sameDefaultPoster.getEmail());
    // assertEquals("The poster's email is incorrect", DEFAULT_EMAIL,
    // sameDefaultPoster.getEmail());
    // assertEquals("The poster's oid is incorrect", this.poster.getOid(),
    // sameDefaultPoster.getOid());
    // assertEquals("The objects must be the same by the equals method",
    // this.poster, sameDefaultPoster);
    // assertEquals("The hashcode must be the same", this.poster.hashCode(),
    // sameDefaultPoster.hashCode());
    //
    // } catch (PersistenceLayerException e) {
    // assertNull("No exception should be thrown while comparing 2 objects", e);
    // }
    // }

    // public void testCreateDuplicatePoster() {
    // System.out.println("########################### TEST CASE CREATE
    // DUPLICATE ##################################");
    // try {
    // ForumPoster samePoster =
    // this.controller.loadPoster(this.poster.getOid());
    // if (samePoster != null) {
    // assertEquals(this.poster, samePoster);
    // }
    // } catch (PersistenceLayerException e) {
    //
    // }
    // }
}

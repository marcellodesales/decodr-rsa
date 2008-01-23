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
public class ForumPosterServiceTest extends TestCase {

    private ForumController controller;

    private ForumPoster poster;

    private final String DEFAULT_NAME = "Marcello Sales";

    private final String DEFAULT_EMAIL = "marcello.sales@gmail.com";

    private final String DEFAULT_PASSWORD = "utn29oad";

    public ForumPosterServiceTest() {
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

        ForumPoster oldPoster = this.controller.loadPosterByEmail(DEFAULT_EMAIL);
        if (!(oldPoster != null && oldPoster.getEmail() != null && DEFAULT_EMAIL.equals(oldPoster.getEmail()))) {
            this.poster = this.controller.createPoster(DEFAULT_NAME, DEFAULT_EMAIL, DEFAULT_PASSWORD, 2 ,3);
        } else {
            this.poster = oldPoster;
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

        if (this.poster != null) {
            ForumPoster oldPoster = this.controller.loadPosterByEmail(DEFAULT_EMAIL);
            if (oldPoster != null && oldPoster.getEmail() != null && this.poster.equals(oldPoster)) {
                this.controller.deletePoster(oldPoster);
            }
            this.poster = null;
        }
    }

    public void testCreatePoster() {
        System.out.println("########################### TEST CASE CREATE ##################################");
        assertEquals("The name of the poster is incorrect", DEFAULT_NAME, poster.getName());
        assertEquals("The email of the poster is incorrect", DEFAULT_EMAIL, poster.getEmail());
        assertEquals("The password of the poster is incorrect", DEFAULT_PASSWORD, poster.getPassword());
    }

    public void testDeletionOfPoster() {
        System.out.println("########################### TEST CASE DELETE ##################################");
        try {
            this.controller.deletePoster(this.poster);
            ForumPoster poster = this.controller.loadPosterByEmail(DEFAULT_EMAIL);
            assertNull("The poster was not properly deleted from the database", poster);

        } catch (PersistenceLayerException e) {
            assertNotNull("The object can't be found since it was deleted", e);
        }
    }

    public void testUpdatePoster() {
        System.out.println("########################### TEST CASE UPDATE ##################################");
        try {
            String CHANGED_NAME = "changed.email@gmail.com";
            this.poster.setName(CHANGED_NAME);
            ForumController.getInstance().savePoster(this.poster);
            ForumPoster changedPoster = ForumController.getInstance().loadPosterByEmail(DEFAULT_EMAIL);
            assertNotNull("The poster was not correctly loaded by email", changedPoster);
            assertEquals("The poster's email was not correctly changed", CHANGED_NAME, changedPoster.getName());

        } catch (PersistenceLayerException e) {
            assertNull(e);
        }
    }

    public void testEqualityOfPosters() {
        System.out.println("########################### TEST CASE EQUALITY ##################################");
        try {
            ForumPoster sameDefaultPoster = this.controller.loadPosterByEmail(this.poster.getEmail());
            assertNotNull("Same poster not loaded", sameDefaultPoster);

            assertNotNull("Same poster did not load the property email", sameDefaultPoster.getEmail());
            assertEquals("The poster's email is incorrect", DEFAULT_EMAIL, sameDefaultPoster.getEmail());
            assertEquals("The poster's oid is incorrect", this.poster.getOid(), sameDefaultPoster.getOid());
            assertEquals("The objects must be the same by the equals method", this.poster, sameDefaultPoster);
            assertEquals("The hashcode must be the same", this.poster.hashCode(), sameDefaultPoster.hashCode());

        } catch (PersistenceLayerException e) {
            assertNull("No exception should be thrown while comparing 2 objects", e);
        }
    }

    public void testCreateDuplicatePoster() {
        System.out.println("########################### TEST CASE CREATE DUPLICATE ##################################");
        try {
            ForumPoster samePoster = this.controller.loadPoster(this.poster.getOid());
            if (samePoster != null) {
                assertEquals(this.poster, samePoster);
            }
        } catch (PersistenceLayerException e) {

        }
    }
}

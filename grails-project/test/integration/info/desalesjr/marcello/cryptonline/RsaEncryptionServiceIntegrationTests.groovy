package info.desalesjr.marcello.cryptonline

import grails.test.*

class RsaEncryptionServiceIntegrationTests extends GrailsUnitTestCase {
    
    def rsaEncryptionService
    
    protected void setUp() {

    }

    void testRsaKeysCreation() {
        
        User user = new User()
        user.firstName = "Marcello"
        user.lastName = "de Sales"
        user.email = "marcello.desales@gmail.com"
        user.password = "1234"
        
        rsaEncryptionService.createRsaKeys(user)
        
        assertNotNull("User Keys must be defined when constructed with random", user.userKeys)
        assertNotNull("The logs must be created", user.userKeys.creationLogs)
        for(String entry : user.userKeys.creationLogs) {
            assertNotNull("Entry cannot be null", entry)
        }
    }
}

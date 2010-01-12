package info.desalesjr.marcello.cryptonline

import info.desalesjr.marcello.cryptonline.UserKeyLogEntry;
import info.desalesjr.marcello.cryptonline.UserKeys;
import info.desalesjr.marcello.cryptonline.rsa.Rsa;

class RsaEncryptionService {

    boolean transactional = true
    
    def void createRsaKeys(User user) {
        Rsa userRsa = Rsa.newInstance()

        UserKeys keys = new UserKeys()
        keys.publicKeyN = userRsa.publicKey.getKeyN()
        keys.publicKeyE = userRsa.publicKey.getKeyE()
        keys.privateKeyD = userRsa.privateKey.getKeyD()

        for(String logEntry : userRsa.getLogEntries()) {
            UserKeyLogEntry userEntry = new UserKeyLogEntry()
            userEntry.entry = logEntry
            keys.addToCreationLogs(userEntry)
        }

        user.userKeys = keys
    }
    
    
}

package info.desalesjr.marcello.cryptonline

class UserKeys {
    
    /**
     * The composed logs from the creation of the keys
     */
    static hasMany = [creationLogs: UserKeyLogEntry]
    /**
     * The public key N
     */
    long publicKeyN
    /**
     * The public key E
     */
    long publicKeyE
    /**
     * The publick key D
     */
    long privateKeyD
    
    static constraints = {
    }
}

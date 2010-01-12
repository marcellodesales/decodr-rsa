package info.desalesjr.marcello.cryptonline

class UserKeys {
    
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

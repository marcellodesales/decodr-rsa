package info.desalesjr.marcello.cryptonline

class UserKeyLogEntry {
    
    static belongsTo = [userKeys:UserKeys]

    /**
     * Each line of the keys creation
     */
    String entry
    
    static constraints = {
    }
}

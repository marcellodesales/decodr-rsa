package info.desalesjr.marcello.cryptonline

import info.desalesjr.marcello.cryptonline.UserKeys;

/**
 * Defines the user of the system
 * @author marcello
 */
class User {
  
    /**
     * The first name of the user
     */
    String firstName
    /**
     * The last name of the user
     */
    String lastName
    /**
     * The email address of the user
     */
    String email
    /**
     * The password of the user 
     */
    String password
    /**
     * User's RSA keys
     */
    UserKeys userKeys
    
    static constraints = {
    }

}

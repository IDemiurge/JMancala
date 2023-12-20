package mancala.common.exception;

/**
 * 
 */
public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String tabId) {
        super("Associated tabId: " + tabId);
    }
}

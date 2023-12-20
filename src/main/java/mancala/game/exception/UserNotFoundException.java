package mancala.game.exception;

/**
 * Created by Alexander on 12/18/2023
 */
public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String tabId) {
        super("Associated tabId: " + tabId);
    }
}

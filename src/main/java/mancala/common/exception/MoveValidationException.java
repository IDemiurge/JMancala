package mancala.common.exception;

/**
 * 
 */
public class MoveValidationException extends RuntimeException{
    public MoveValidationException(String tabId) {
        super("Associated tabId: " + tabId);
    }
}

package mancala.game.exception;

/**
 * Created by Alexander on 12/18/2023
 */
public class MoveValidationException extends RuntimeException{
    public MoveValidationException(String tabId) {
        super("Associated tabId: " + tabId);
    }
}

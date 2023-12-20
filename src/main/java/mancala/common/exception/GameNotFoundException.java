package mancala.common.exception;

/**
 * 
 */
public class GameNotFoundException extends RuntimeException{
    public GameNotFoundException(String gameId) {
        super("Game id: " + gameId);
    }
}

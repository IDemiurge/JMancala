package mancala.common.exception;

/**
 * 
 */
public class GameRoomException extends RuntimeException{
    public GameRoomException(String gameId) {
        super(gameId);
    }
}

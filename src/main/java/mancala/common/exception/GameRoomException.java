package mancala.common.exception;

/**
 * Created by Alexander on 12/18/2023
 */
public class GameRoomException extends RuntimeException{
    public GameRoomException(String gameId) {
        super(gameId);
    }
}

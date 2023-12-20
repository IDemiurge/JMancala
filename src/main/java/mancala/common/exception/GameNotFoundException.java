package mancala.common.exception;

/**
 * Created by Alexander on 12/18/2023
 */
public class GameNotFoundException extends RuntimeException{
    public GameNotFoundException(String gameId) {
        super("Game id: " + gameId);
    }
}

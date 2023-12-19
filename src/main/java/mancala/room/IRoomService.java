package mancala.room;

import mancala.game.logic.setup.MancalaGameMode;
import mancala.game.logic.state.GameState;

import java.util.List;

public interface IRoomService {
    List<Room> fetchGames();

    GameState getGameState(String gameId);

    Room getGameRoom(String gameId);

    String createNewGame(String hostUserName, MancalaGameMode gameMode);

    int joinGame(String gameId, String userName);

    GameState startGame(String gameId);

    GameState makeMove(String gameId, int pitIndex);

    boolean isReadyToStart(String gameId);
}

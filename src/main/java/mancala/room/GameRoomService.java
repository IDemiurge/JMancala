package mancala.room;

import mancala.game.IGameService;
import mancala.game.logic.setup.MancalaGameMode;
import mancala.game.logic.state.GameState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Alexander on 12/12/2023
 */
@Service
public class GameRoomService {
    @Autowired
    IGameService gameService;
    @Autowired
    Comparator<GameRoom> gameRoomSorter;

    private Map<String, GameState> activeGames = new ConcurrentHashMap<>();
    private Map<String, GameRoom> rooms = new ConcurrentHashMap<>();

    public List<GameRoom> fetchGames() {
        List<GameRoom> gameRooms = new ArrayList<>(rooms.values());
        gameRooms.add(new GameRoom("Test Room"));
        Collections.sort(gameRooms, gameRoomSorter);
        return gameRooms;
    }

    public GameState createNewGame(String hostUserName, MancalaGameMode gameMode) {
        //TODO mode!
        GameState gameState = gameService.host(hostUserName);
        GameRoom gameRoom = new GameRoom(hostUserName);
        gameRoom.setGameMode(gameMode);
        rooms.put(gameState.identifier(), gameRoom);
        //TODO only create state when game is started!
        activeGames.put(gameState.identifier(), gameState);
        return gameState;
    }

    public GameState joinGame(String gameId, String userName) {
        GameState state = activeGames.get(gameId);
        if (state == null)
            return null;
        gameService.join(state, userName);
        //if enough players...?
        return state;
    }

    public GameState makeMove(String gameId, int pitIndex) {
        GameState state = activeGames.get(gameId);
        state = gameService.makeMove(state, pitIndex);
        if (state.gameOver()) {
            activeGames.remove(gameId);
        } else {
            activeGames.put(gameId, state);
        }
        return state;
    }

}

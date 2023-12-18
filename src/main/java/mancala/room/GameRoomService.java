package mancala.room;

import lombok.extern.slf4j.Slf4j;
import mancala.game.GameData;
import mancala.game.IGameService;
import mancala.game.exception.GameNotFoundException;
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
@Slf4j
public class GameRoomService {
    @Autowired
    IGameService gameService;
    @Autowired
    Comparator<GameRoom> gameRoomSorter;
    @Autowired
    GameIdGenerator idGenerator;

    private Map<String, GameState> activeGames = new ConcurrentHashMap<>();
    private Map<String, GameRoom> rooms = new ConcurrentHashMap<>();

    public List<GameRoom> fetchGames() {
        List<GameRoom> gameRooms = new ArrayList<>(rooms.values());
        gameRooms.add(new GameRoom("Test Room"));
        Collections.sort(gameRooms, gameRoomSorter);
        return gameRooms;
    }

    public GameState getGameState(String gameId) {
        return activeGames.get(gameId);
    }

    public String createNewGame(String hostUserName, MancalaGameMode gameMode) {
        String identifier = idGenerator.generateIdentifier(hostUserName, gameMode);
        GameRoom gameRoom = new GameRoom(hostUserName);
        gameRoom.setGameMode(gameMode);
        rooms.put(identifier, gameRoom);
        gameRoom.setId(identifier);
        log.info("Game room created: " + gameRoom);
        return identifier;
    }

    public boolean joinGame(String gameId, String userName) {
        GameRoom room = rooms.get(gameId);
        if (room == null)
            return false;
        room.getPlayers().add(userName);
        log.info(room.getHostUserName()+ "'s Game room joined by: " + userName);
        return true;
    }

    public GameState startGame(String gameId) {
        GameRoom room = rooms.get(gameId);
        if (room == null)
            throw new GameNotFoundException(gameId);
        GameData data = new GameData(gameId, room.getPlayers(), room.getGameMode());
        GameState gameState = gameService.startGame(data);
        activeGames.put(gameId, gameState);
        log.info(room.getHostUserName()+ "'s Game started with state: " + gameState);
        return gameState;
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

    public boolean isReadyToStart(String gameId) {
        return rooms.get(gameId).getPlayers().size() >= getMinPlayers(  rooms.get(gameId));
    }

    private int getMinPlayers(GameRoom gameRoom) {
        //TODO
        return 2;
    }
}

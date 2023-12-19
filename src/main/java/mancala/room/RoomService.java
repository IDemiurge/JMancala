package mancala.room;

import lombok.extern.slf4j.Slf4j;
import mancala.game.GameSetupData;
import mancala.game.IGameService;
import mancala.game.exception.GameNotFoundException;
import mancala.game.logic.setup.IMancalaSetupProvider;
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
public class RoomService implements IRoomService {
    @Autowired
    IGameService gameService;
    @Autowired
    Comparator<Room> gameRoomSorter;
    @Autowired
    GameIdGenerator idGenerator;
    @Autowired
    IMancalaSetupProvider provider;

    private Map<String, GameState> activeGames = new ConcurrentHashMap<>();
    private Map<String, Room> rooms = new ConcurrentHashMap<>();

    @Override
    public List<Room> fetchGames() {
        List<Room> rooms = new ArrayList<>(this.rooms.values());
        Collections.sort(rooms, gameRoomSorter);
        rooms.removeIf(room -> room.isFull());
        return rooms;
    }

    @Override
    public GameState getGameState(String gameId) {
        return activeGames.get(gameId);
    }

    @Override
    public Room getGameRoom(String gameId) {
        return rooms.get(gameId);
    }

    @Override
    public String createNewGame(String hostUserName, MancalaGameMode gameMode) {
        String identifier = idGenerator.generateIdentifier(hostUserName, gameMode);
        Room room = new Room(hostUserName);
        room.setGameMode(gameMode);
        room.setGameSetup(provider.createSetup(gameMode));
        rooms.put(identifier, room);
        room.setId(identifier);

        log.info("Game room created: " + room);
        room.getLog().add("TEST MSG");
        return identifier;
    }

    /**
     *
     * @param gameId
     * @param userName
     * @return player's ordinal number
     */
    @Override
    public int joinGame(String gameId, String userName) {
        Room room = rooms.get(gameId);
        if (room == null)
            throw new GameNotFoundException(gameId);
        room.addPlayer(userName);
        log.info(room.getHostUserName()+ "'s Game room joined by: " + userName);
        return room.getPlayers().size()-1;
    }

    @Override
    public GameState startGame(String gameId) {
        Room room = rooms.get(gameId);
        if (room == null)
            throw new GameNotFoundException(gameId);
        GameSetupData data = new GameSetupData(gameId, room.getPlayers(), room.getGameMode());
        GameState gameState = gameService.startGame(data);
        activeGames.put(gameId, gameState);
        log.info(room.getHostUserName()+ "'s Game started with state: " + gameState);
        return gameState;
    }

    @Override
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

    @Override
    public boolean isReadyToStart(String gameId) {
        return rooms.get(gameId).getPlayers().size() >= getMinPlayers(  rooms.get(gameId));
    }

    private int getMinPlayers(Room room) {
        //TODO
        return 2;
    }
}

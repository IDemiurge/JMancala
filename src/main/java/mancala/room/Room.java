package mancala.room;

import mancala.game.exception.GameRoomException;
import mancala.game.logic.setup.MancalaGameMode;
import mancala.game.logic.setup.MancalaSetup;
import mancala.game.logic.state.GameLog;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Alexander on 12/17/2023
 */
public class Room {
    //TODO id here?
    private String id;
    private String hostUserName;
    private MancalaGameMode gameMode = MancalaGameMode.Classic;
    private Set<String> players = new LinkedHashSet<>();
    private GameLog log;
    private MancalaSetup gameSetup;
    private boolean started;

    public Room(String hostUserName) {
        this.hostUserName = hostUserName;
        this.players.add(hostUserName);
        this.log = new GameLog();
    }

    public GameLog getLog() {
        return log;
    }

    public String getHostUserName() {
        return hostUserName;
    }

    public MancalaGameMode getGameMode() {
        return gameMode;
    }

    public Set<String> getPlayers() {
        return players;
    }

    public void setGameMode(MancalaGameMode gameMode) {
        this.gameMode = gameMode;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public boolean isFull() {
        return players.size() == gameSetup.numberOfPlayers();
    }

    public void setGameSetup(MancalaSetup gameSetup) {
        this.gameSetup = gameSetup;
    }

    public MancalaSetup getGameSetup() {
        return gameSetup;
    }

    public void addPlayer(String userName) {
        if (isFull()) {
            throw new GameRoomException("Cannot add more than players " +
                    gameSetup.numberOfPlayers() +
                    " with game mode " + gameMode);
        }
        players.add(userName);
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
}

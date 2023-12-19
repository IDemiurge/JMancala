package mancala.room;

import mancala.game.logic.setup.MancalaGameMode;
import mancala.game.logic.state.GameLog;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Alexander on 12/17/2023
 */
public class GameRoom {
    //TODO id here?
    private String id;
    private String hostUserName;
    private MancalaGameMode gameMode = MancalaGameMode.Classic;
    private Set<String> players = new LinkedHashSet<>();
    private GameLog log;

    public GameRoom(String hostUserName) {
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
}

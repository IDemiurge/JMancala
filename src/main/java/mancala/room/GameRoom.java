package mancala.room;

import mancala.game.logic.setup.MancalaGameMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander on 12/17/2023
 */
public class GameRoom {
    private String hostUserName;
    private MancalaGameMode gameMode =MancalaGameMode.Classic;
    private List<String> players=     new ArrayList<>() ;

    public GameRoom(String hostUserName) {
        this.hostUserName = hostUserName;
        players.add(hostUserName);
    }

    public String getHostUserName() {
        return hostUserName;
    }

    public MancalaGameMode getGameMode() {
        return gameMode;
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setGameMode(MancalaGameMode gameMode) {
        this.gameMode = gameMode;
    }
}

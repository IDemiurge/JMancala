package mancala.web.room;

import mancala.common.MancalaGameMode;

/**
 * Created by Alexander on 12/18/2023
 */
public class GameIdGenerator {

    public String generateIdentifier(String hostUserName, MancalaGameMode gameMode) {
        return hostUserName + "-" + gameMode.abbreviation;
    }
}

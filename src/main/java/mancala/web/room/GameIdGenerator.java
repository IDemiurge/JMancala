package mancala.web.room;

import mancala.common.MancalaGameMode;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 */
public class GameIdGenerator {
    AtomicInteger counter = new AtomicInteger();

    public String generateIdentifier(String hostUserName, MancalaGameMode gameMode) {
        return "GoodGame_Number_" + counter.incrementAndGet() + "_Mode-" + gameMode.toString();
    }
}

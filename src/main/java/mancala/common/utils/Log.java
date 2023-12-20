package mancala.common.utils;

import lombok.extern.slf4j.Slf4j;
import mancala.common.exception.GameNotFoundException;
import mancala.engine.logic.state.GameLog;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 */
@Slf4j
public class Log {

    static Map<String, GameLog> gameLogMap = new ConcurrentHashMap<>();

    public static void registerLog(String gameId, GameLog log) {
        gameLogMap.put(gameId, log);
    }

    public static void info(String gameId, String msg) {
        GameLog gameLog = getLog(gameId);
        gameLog.addMessage(msg);
    }

    public static GameLog getLog(String gameId) {
        GameLog gameLog = gameLogMap.get(gameId);
        if (gameLog == null) {
            throw new GameNotFoundException(gameId);
        }
        return gameLog;
    }
}

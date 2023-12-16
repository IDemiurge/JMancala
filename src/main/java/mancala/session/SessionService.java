package mancala.session;

import mancala.game.GameService;
import mancala.game.IGameService;
import mancala.game.logic.state.GameState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Alexander on 12/12/2023
 */
@Service
public class SessionService {
    @Autowired
    IGameService gameService;

    Map<String, GameState> games = new ConcurrentHashMap<>();

    public GameState createNewGame() {
        GameState gameState = gameService.host();
        return gameState;
    }

    public GameState joinGame(String gameId) {
        GameState state = games.get(gameId);
        if (state==null)
            return null;
        gameService.join(state);
        //if enough players...?
        return state;
    }

    public GameState makeMove(String gameId, int pitIndex) {
        GameState state = games.get(gameId);
        state = gameService.makeMove(state, pitIndex);
        if (state.gameOver()){
            games.remove(gameId);
        } else {
            games.put(gameId, state);
        }
        return state;
    }
}

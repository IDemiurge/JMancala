package mancala.web.game;

import lombok.extern.slf4j.Slf4j;
import mancala.engine.logic.handler.GameHandler;
import mancala.engine.logic.handler.ITipHandler;
import mancala.engine.logic.handler.PitHandler;
import mancala.engine.logic.setup.GameSetupData;
import mancala.engine.logic.setup.IMancalaSetupProvider;
import mancala.engine.logic.setup.MancalaSetup;
import mancala.engine.logic.state.GameLog;
import mancala.engine.logic.state.GameState;
import mancala.engine.logic.state.TurnState;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class GameService implements IGameService {

    @Autowired
    private IMancalaSetupProvider setupProvider;

    private Map<String, GameHandler> gameHandlers = new ConcurrentHashMap<>();

    @Override
    public GameState startGame(GameSetupData data, GameLog gameLog) {
        GameHandler handler = createGameHandler(data, gameLog);
        GameState state = handler.createStartingGameState();

        gameHandlers.put(state.identifier(), handler);
        
        String message = "\nStarted a Game:" + data.identifier();
        log.info(message);
        if (gameLog!=null){
            gameLog.addMessage(message);
        }
        return state;
    }

    @Override
    public GameState startGame(GameSetupData data) {
        return startGame(data, null);
    }

    @Override
    public GameState makeMove(GameState state, int pitIndex) {
        GameHandler handler = gameHandlers.get(state.identifier());

        log.info("\n" + state.currentPlayerName() + " player move: " + pitIndex);

        TurnState turnState = handler.moveStones(state, pitIndex);
        log.info("\nTurn done");

        return handler.createNewGameState(turnState, state);
    }


    public GameHandler createGameHandler(GameSetupData data, GameLog gameLog) {
        MancalaSetup setup = setupProvider.createSetup(data.mode());
        PitHandler pitHandler = setupProvider.createPitHandler(data.mode());
        ITipHandler tipHandler = setupProvider.createTipHandler(data.mode());
        return new GameHandler(data, pitHandler, tipHandler, setup, gameLog);

    }



}

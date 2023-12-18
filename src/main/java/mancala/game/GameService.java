package mancala.game;

import lombok.extern.slf4j.Slf4j;
import mancala.game.logic.handler.GameHandler;
import mancala.game.logic.handler.ITipHandler;
import mancala.game.logic.handler.PitHandler;
import mancala.game.logic.setup.IMancalaSetupProvider;
import mancala.game.logic.setup.MancalaSetup;
import mancala.game.logic.state.GameState;
import mancala.game.logic.state.TurnState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static mancala.game.logic.state.TurnState.StateType.NORMAL;

@Service
@Slf4j
public class GameService implements IGameService {

    @Autowired
    IMancalaSetupProvider setupProvider;

    Map<String, GameHandler> gameHandlers = new ConcurrentHashMap<>();

    @Override
    public GameState startGame(GameData data) {
        log.info("\nStarted a Game:" + data.identifier());
        GameHandler handler = createGameHandler(data);
        GameState state = handler.createStartingGameState();
        gameHandlers.put(state.identifier(), handler);
        return state;
    }

    @Override
    public GameState makeMove(GameState state, int pitIndex) {
        //TODO validate input?
        GameHandler handler = gameHandlers.get(state.identifier());

        log.info("\n" + state.currentPlayerName() + " player move: " + pitIndex);
        handler.logState(state);
        TurnState turnState = handler.placeStone(createStartTurnState(state, pitIndex));
        log.info("\nTurn done");
        handler.logState(state);
        return createNewGameState(handler, turnState, state);
    }

    private TurnState createStartTurnState(GameState state, int pitIndex) {
        int inHand = state.pits()[pitIndex];
        state.pits()[pitIndex] = 0;
        return new TurnState(state.pits(), inHand, state.currentPlayer(), ++pitIndex, NORMAL);
    }

    public GameHandler createGameHandler(GameData data) {
        MancalaSetup setup = setupProvider.createSetup(data.mode());
        PitHandler pitHandler = setupProvider.createPitHandler(data.mode());
        ITipHandler tipHandler = setupProvider.createTipHandler(data.mode());
        return new GameHandler(data, pitHandler, tipHandler, setup);

    }

    //TODO refactor
    public GameState createNewGameState(GameHandler handler, TurnState turnState, GameState state) {
        int currentPlayer = turnState.playerIndex();
        int turn = state.turnNumber();
        GameState.Builder builder = GameState.builder()
                .copyFields(state)
                .pits(turnState.pits())
                .statusMessage(handler.getTip(turnState, state));

        switch (turnState.type()) {
            case PLAYER_DONE:
                if (turnState.playerIndex() == 0) {
                    turn++;  //increment turnNumber only when host moves
                }
                currentPlayer = handler.nextPlayer(turnState.playerIndex());
            case NORMAL:
                return builder
                        .currentPlayer(currentPlayer)
                        .turnNumber(turn)
                        .build();
            case GAME_OVER:
                return builder
                        .currentPlayer(currentPlayer)
                        .turnNumber(turn)
                        .victor(state.players().get(turnState.getWinnerIndex()))
                        .build();
        }
        throw new RuntimeException("Unknown state type: " + turnState.type());
    }


}

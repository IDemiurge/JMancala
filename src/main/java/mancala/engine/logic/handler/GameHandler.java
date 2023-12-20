package mancala.engine.logic.handler;

import lombok.extern.slf4j.Slf4j;
import mancala.common.utils.Log;
import mancala.common.utils.MancalaStringUtils;
import mancala.engine.logic.setup.GameSetupData;
import mancala.engine.logic.setup.MancalaSetup;
import mancala.engine.logic.state.GameState;
import mancala.engine.logic.state.GameStateBuilder;
import mancala.engine.logic.state.TurnState;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Slf4j
public class GameHandler implements IGameHandler {
    private final GameSetupData data;
    private final IPitHandler pitHandler;
    private final ITipHandler tipHandler;
    private final MancalaSetup setup;

    public GameHandler(GameSetupData data, IPitHandler pitHandler, ITipHandler tipHandler, MancalaSetup setup) {
        this.data = data;
        this.pitHandler = pitHandler;
        this.tipHandler = tipHandler;
        this.setup = setup;
    }

    @Override
    public GameState start() {
        String message = "\nStarting a Game:" + data.identifier();
        log(message);
        List<String> players = new ArrayList<>(data.players());
        return GameState.builder()
                .turnNumber(1)
                .pits(setup.startingPits())
                .players(players)
                .statusMessage(tipHandler.getStartingTip(players.get(0)))
                .currentPlayer(0)
                .identifier(data.identifier())
                .build();
    }

    @Override
    public int nextPlayer(int playerIndex) {
        return (playerIndex + 1) % setup.numberOfPlayers();
    }

    @Override
    public TurnState moveStones(GameState state, int pitIndex) {

        logState(state);
        TurnState turnState = pitHandler.moveStones(state, pitIndex);
        return turnState;
    }

    @Override
    public GameState createNewGameState(TurnState turnState, GameState state) {
        GameStateBuilder builder = GameStateBuilder.compose(this, turnState, state);

        String currentPlayerName = builder.getPlayers().get(builder.getCurrentPlayer());
        int turnNumber = builder.getTurnNumber();
        builder.statusMessage(tipHandler.getStatusMessage(
                currentPlayerName,
                turnNumber));

        return builder.build();
    }

    private void log(String message) {
        log.info(message);
        Log.info(data.identifier(), message);
    }

    private void logState(GameState state) {
        log.info("\nState: " + MancalaStringUtils.prettyPits(state.pits(), setup));
    }
}

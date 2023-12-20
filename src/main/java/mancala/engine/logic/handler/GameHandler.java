package mancala.engine.logic.handler;

import lombok.extern.slf4j.Slf4j;
import mancala.common.utils.MancalaStringUtils;
import mancala.engine.logic.setup.GameSetupData;
import mancala.engine.logic.setup.MancalaSetup;
import mancala.engine.logic.state.GameLog;
import mancala.engine.logic.state.GameState;
import mancala.engine.logic.state.GameStateBuilder;
import mancala.engine.logic.state.TurnState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander on 12/18/2023
 */
@Slf4j
public class GameHandler {
    private final GameSetupData data;
    private final PitHandler pitHandler;
    private final ITipHandler tipHandler;
    private final MancalaSetup setup;
    private final GameLog gameLog;


    public GameHandler(GameSetupData data, PitHandler pitHandler, ITipHandler tipHandler, MancalaSetup setup,
                       GameLog gameLog) {
        this.data = data;
        this.pitHandler = pitHandler;
        this.tipHandler = tipHandler;
        this.setup = setup;
        this.gameLog = gameLog;
    }


    public GameState createStartingGameState() {
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

    public int nextPlayer(int playerIndex) {
        return (playerIndex + 1) % setup.numberOfPlayers();
    }

    public TurnState moveStones(GameState state, int pitIndex) {

        logState(state);
        TurnState turnState = pitHandler.moveStones(state, pitIndex);
        return turnState;
    }

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
        log.info("\nState: " + message);
        if (gameLog != null) {
            gameLog.addMessage(message);
        }
    }
    private void logState(GameState state) {
        log(MancalaStringUtils.prettyPits(state.pits(), setup));
    }
}

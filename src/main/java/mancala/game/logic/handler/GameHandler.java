package mancala.game.logic.handler;

import lombok.extern.slf4j.Slf4j;
import mancala.game.GameSetupData;
import mancala.game.logic.setup.MancalaSetup;
import mancala.game.logic.state.GameState;
import mancala.game.logic.state.TurnState;

import java.util.ArrayList;
import java.util.List;

import static mancala.game.logic.state.TurnState.StateType.NORMAL;

/**
 * Created by Alexander on 12/18/2023
 */
@Slf4j
public class GameHandler {
    private final GameSetupData data;
    private final PitHandler pitHandler;
    private final ITipHandler tipHandler;
    private final MancalaSetup setup;
    // GameHistory history;

    public GameHandler(GameSetupData data, PitHandler pitHandler, ITipHandler tipHandler, MancalaSetup setup) {
        this.data = data;
        this.pitHandler = pitHandler;
        this.tipHandler = tipHandler;
        this.setup = setup;
    }

    //TODO history & game log
    public void logState(GameState state) {
        StringBuilder builder = new StringBuilder();
        builder.append(mancala.game.utils.MancalaStringUtils.prettyPits(state.pits(), setup));
        log.info("\nState: " + builder);
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

    public String getTip(TurnState turnState, GameState state) {
        return tipHandler.getTip(turnState, state);
    }


    public TurnState moveStones(GameState state, int pitIndex) {
       return pitHandler.moveStones(state, pitIndex);
    }

}

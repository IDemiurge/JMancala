package mancala.game.logic.handler;

import lombok.extern.slf4j.Slf4j;
import mancala.game.GameData;
import mancala.game.logic.setup.MancalaSetup;
import mancala.game.logic.state.GameState;
import mancala.game.logic.state.TurnState;

/**
 * Created by Alexander on 12/18/2023
 */
@Slf4j
public class GameHandler {
    private final GameData data;
    private final PitHandler pitHandler;
    private final ITipHandler tipHandler;
    private final MancalaSetup setup;
    // GameHistory history;

    public GameHandler(GameData data, PitHandler pitHandler, ITipHandler tipHandler, MancalaSetup setup) {
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
        return GameState.builder()
                .turnNumber(1)
                .pits(setup.startingPits())
                .players(data.players())
                .statusMessage(tipHandler.getStartingTip(data.players().get(0)))
                .currentPlayer(0)
                .identifier(data.identifier())
                .build();
    }

    public int nextPlayer(int playerIndex) {
        return (playerIndex + 1) % setup.playersNumber();
    }

    public String getTip(TurnState turnState, GameState state) {
        return tipHandler.getTip(turnState, state);
    }

    public TurnState placeStone(TurnState turnState) {
        return pitHandler.placeStone(turnState);
    }
}

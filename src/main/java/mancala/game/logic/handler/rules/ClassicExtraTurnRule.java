package mancala.game.logic.handler.rules;

import mancala.game.logic.setup.ClassicMancalaSetup;
import mancala.game.logic.state.TurnState;
import mancala.game.utils.MancalaUtils;

/**
 * Created by Alexander on 12/13/2023
 */
public class ClassicExtraTurnRule implements ExtraTurnRule {
    @Override
    public TurnState enact(TurnState state) {
        //change nothing, just return state to start the same player's turn over
        return state;
    }

    @Override
    public boolean check(TurnState state) {
        return state.pitIndex() == MancalaUtils.getPlayerStoreIndex(ClassicMancalaSetup.PITS_PER_PLAYER, state.playerIndex());
    }
}

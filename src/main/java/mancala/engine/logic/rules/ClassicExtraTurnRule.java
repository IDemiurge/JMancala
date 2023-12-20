package mancala.engine.logic.rules;

import mancala.common.utils.Log;
import mancala.common.utils.MancalaMathUtils;
import mancala.engine.logic.setup.ClassicMancalaSetup;
import mancala.engine.logic.setup.MancalaSetup;
import mancala.engine.logic.state.TurnState;

/**
 * 
 */
public class ClassicExtraTurnRule implements ExtraTurnRule {

    private final MancalaSetup setup;

    public ClassicExtraTurnRule(MancalaSetup setup) {
        this.setup = setup;
    }
    @Override
    public TurnState enact(TurnState state) {
        Log.info(state.gameIdentifier(), "Extra turn for player #"+state.playerIndex());
        return state.addStone(state.pitIndex());
    }

    @Override
    public boolean check(TurnState state) {
        return state.pitIndex() == MancalaMathUtils.getPlayerStoreIndex(ClassicMancalaSetup.PITS_PER_PLAYER, state.playerIndex());
    }
}

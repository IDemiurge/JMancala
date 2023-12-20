package mancala.engine.logic.rules;

import mancala.common.utils.Log;
import mancala.engine.logic.setup.MancalaSetup;
import mancala.engine.logic.state.TurnState;
import mancala.common.utils.MancalaMathUtils;

public class ClassicCaptureRule implements CaptureRule {

    private final MancalaSetup setup;

    public ClassicCaptureRule(MancalaSetup setup) {
        this.setup = setup;
    }

    @Override
    public TurnState enact(TurnState state) {
        TurnState clone = state.clone();
        int index = MancalaMathUtils.getOppositePit(setup.pitsPerPlayer(),
                state.pitIndex(), state.playerIndex(), setup.pitsTotal());
        int captured = clone.pits()[index];
        Log.info(state.gameIdentifier(), "Captured " + captured + " stones from opposite pit #" + index);
        clone.pits()[index] = 0;
        clone.pits()[setup.stores()[clone.playerIndex()]] += captured + 1; //account for the final stone itself
        return state;
    }

    @Override
    public boolean check(TurnState state) {
        if (setup.stores()[0] == state.pitIndex() || setup.stores()[1] == state.pitIndex())
            return false; //not for stores
        if (state.pits()[state.pitIndex()] != 0) {
            return false; //only for empty pits
        }
        //only for player's own pits
        if (!setup.isPlayersPit(state.playerIndex(), state.pitIndex())){
            return false;
        }
        return true;


    }
}

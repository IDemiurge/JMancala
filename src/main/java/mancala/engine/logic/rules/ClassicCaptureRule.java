package mancala.engine.logic.rules;

import lombok.extern.slf4j.Slf4j;
import mancala.engine.logic.setup.ClassicMancalaSetup;
import mancala.engine.logic.state.TurnState;
import mancala.common.utils.MancalaMathUtils;

import static mancala.engine.logic.setup.ClassicMancalaSetup.*;

@Slf4j
public class ClassicCaptureRule implements CaptureRule {
    @Override
    public TurnState enact(TurnState state) {
        TurnState clone = state.clone();
        int index = MancalaMathUtils.getOppositePit(PITS_PER_PLAYER,
                state.pitIndex(), state.playerIndex(), PITS_TOTAL);
        int captured = clone.pits()[index];
        log.info("Captured " + captured + " stones from opposite pit #" + index);
        clone.pits()[index] = 0;
        clone.pits()[STORES[clone.playerIndex()]] += captured + 1; //account for the final stone itself
        return state;
    }

    @Override
    public boolean check(TurnState state) {
        if (STORES[0] == state.pitIndex() || STORES[1] == state.pitIndex())
            return false; //not for stores
        if (state.pits()[state.pitIndex()] != 0) {
            return false; //only for empty pits
        }
        //only for player's own pits
        if (state.playerIndex()==0) {
            if (state.pitIndex()>5) {
                return false;
            }
        }
        if (state.playerIndex()==1) {
            if (state.pitIndex()<7) {
                return false;
            }
        }
        return true;


    }
}

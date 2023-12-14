package mancala.game.logic.handler.rules;

import mancala.game.logic.setup.ClassicMancalaSetup;
import mancala.game.logic.state.TurnState;
import mancala.game.utils.MancalaUtils;

public class ClassicCaptureRule implements CaptureRule {
    @Override
    public TurnState enact(TurnState state) {
        TurnState clone = state.clone();
        int index = MancalaUtils.getOppositePit(ClassicMancalaSetup.PITS_PER_PLAYER,
                state.pitIndex(), state.playerIndex());
        int captured = clone.pits()[index];
        clone.pits()[index] = 0;
        clone.pits()[ClassicMancalaSetup.STORES[clone.playerIndex()]] += captured;
        return state;
    }

    @Override
    public boolean check(TurnState state) {
        return state.pits()[state.pitIndex()] == 0;
    }
}

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
        clone.pits()[index] = 0;
        return state;
    }

    @Override
    public boolean check(TurnState state) {
        return state.pits()[state.pitIndex()] == 0;
    }
}

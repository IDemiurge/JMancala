package mancala.game.logic.handler.rules;

import mancala.game.logic.state.TurnState;

public interface CaptureRule {
    TurnState enact(TurnState state);

    boolean check(TurnState state);
}

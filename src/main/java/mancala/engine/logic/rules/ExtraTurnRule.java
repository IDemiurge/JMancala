package mancala.engine.logic.rules;

import mancala.engine.logic.state.TurnState;

public interface ExtraTurnRule {
    TurnState enact(TurnState state);

    boolean check(TurnState state);
}

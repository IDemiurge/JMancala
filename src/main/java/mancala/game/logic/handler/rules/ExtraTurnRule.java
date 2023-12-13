package mancala.game.logic.handler.rules;

import mancala.game.logic.state.TurnState;

public interface ExtraTurnRule {
    TurnState enact(TurnState state);

    boolean check(TurnState state);
}

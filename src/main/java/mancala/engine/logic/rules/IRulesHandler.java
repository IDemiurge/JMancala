package mancala.engine.logic.rules;

import mancala.engine.logic.state.TurnState;

public interface IRulesHandler {
    TurnState checkFinalStoneRules(TurnState state);
}

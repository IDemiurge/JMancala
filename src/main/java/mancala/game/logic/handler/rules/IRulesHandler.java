package mancala.game.logic.handler.rules;

import mancala.game.logic.state.TurnState;

public interface IRulesHandler {
    TurnState checkFinalStoneRules(TurnState state);
}

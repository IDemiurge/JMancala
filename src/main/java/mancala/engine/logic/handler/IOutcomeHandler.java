package mancala.engine.logic.handler;

import mancala.engine.logic.state.TurnState;

public interface IOutcomeHandler {
    int getWinner(TurnState state);
}

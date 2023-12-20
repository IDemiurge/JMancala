package mancala.engine.logic.handler;

import mancala.engine.logic.state.GameState;
import mancala.engine.logic.state.TurnState;

public interface IPitHandler {
    TurnState moveStones(GameState state, int pitIndex);
}

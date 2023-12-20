package mancala.engine.logic.handler;

import mancala.engine.logic.state.GameState;
import mancala.engine.logic.state.TurnState;

public interface IGameHandler {
    GameState start();

    int nextPlayer(int playerIndex);

    TurnState moveStones(GameState state, int pitIndex);

    GameState createNewGameState(TurnState turnState, GameState state);
}

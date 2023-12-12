package mancala.game;

import mancala.game.logic.state.GameState;

public interface IGameService {
    GameState host();

    GameState makeMove(GameState gameState, int pitIndex);

    void join(GameState state);
}

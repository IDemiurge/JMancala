package mancala.game;

import mancala.game.logic.state.GameState;

public interface IGameService {
    GameState host();

    GameState host(String playerName);

    GameState makeMove(GameState gameState, int pitIndex);

    void join(GameState state);

    void join(GameState state, String playerName);
}

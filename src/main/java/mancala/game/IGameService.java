package mancala.game;

import mancala.game.logic.state.GameState;

public interface IGameService {
    GameState makeMove(int pitIndex);

    GameState init(int i);
}

package mancala.game;

import mancala.game.logic.state.GameState;

public interface IGameService {

    GameState makeMove(GameState gameState, int pitIndex);

    GameState startGame(GameSetupData data);

}

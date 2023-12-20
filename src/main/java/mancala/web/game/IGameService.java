package mancala.web.game;

import mancala.engine.logic.setup.GameSetupData;
import mancala.engine.logic.state.GameLog;
import mancala.engine.logic.state.GameState;

public interface IGameService {

    GameState makeMove(GameState gameState, int pitIndex);

    GameState startGame(GameSetupData data, GameLog gameLog);

    GameState startGame(GameSetupData data);

}

package mancala.game.logic.handler;


import mancala.game.logic.state.GameState;
import mancala.game.logic.state.TurnState;

public interface ITipHandler {
    String getTip(TurnState turnState, GameState state);

    String getStartingTip(String playerName);
}

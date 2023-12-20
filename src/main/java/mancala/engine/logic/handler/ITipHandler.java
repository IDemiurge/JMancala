package mancala.engine.logic.handler;


import mancala.engine.logic.state.GameState;
import mancala.engine.logic.state.TurnState;

public interface ITipHandler {

    String getStatusMessage(String currentPlayerName, int turnNumber);

    String getStartingTip(String playerName);

    String getGameOverTip(TurnState turnState, GameState state);
}

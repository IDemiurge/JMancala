package mancala.game.logic.handler;


import mancala.game.logic.state.GameState;
import mancala.game.logic.state.TurnState;

/**
 * Created by Alexander on 12/11/2023
 */
public class TipHandler implements ITipHandler {


    @Override
    public String getTip(TurnState turnState, GameState state) {
        // turnState.playerIndex()
        return "Mock tip";
    }

    @Override
    public String getStartingTip(String playerName) {
        return "Game started by " + playerName;
    }
}

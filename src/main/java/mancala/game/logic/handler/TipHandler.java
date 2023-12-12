package mancala.game.logic.handler;

import mancala.game.logic.state.turn.ITurnState;

/**
 * Created by Alexander on 12/11/2023
 */
public class TipHandler implements ITipHandler {

    //TODO
    int playerIndex;

    @Override
    public String getTip(ITurnState turnState) {
        return "Mock tip";
    }

    @Override
    public String getStartingTip() {
        if (playerIndex==0)
            return "Game started with you as a host. Pick a pit!";
        return "Game started with you as a guest. Wait for your turn...";
    }
}

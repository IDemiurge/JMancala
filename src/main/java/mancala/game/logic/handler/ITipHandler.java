package mancala.game.logic.handler;

import mancala.game.logic.state.turn.ITurnState;

public interface ITipHandler {
    String getTip(ITurnState turnState);

    String getStartingTip();
}

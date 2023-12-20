package mancala.engine.logic.handler;


import mancala.engine.logic.setup.ClassicMancalaSetup;
import mancala.engine.logic.state.GameState;
import mancala.engine.logic.state.TurnState;

/**
 * 
 */
public class TipHandler implements ITipHandler {

    @Override
    public String getStatusMessage(String currentPlayerName, int turnNumber) {
        return build("Turn [", turnNumber, "], Active:" , currentPlayerName);
    }

    @Override
    public String getStartingTip(String playerName) {
        return "Game started by " + playerName;
    }

    @Override
    public String getGameOverTip(TurnState turnState, GameState state) {
        return build(
                "Game Over! Victory is claimed by " , state.getWinnerName(turnState) ,
                ", stones collected: " ,
                turnState.pits()[ClassicMancalaSetup.STORES[turnState.getWinnerIndex()]] ,
                " vs " ,
                turnState.pits()[ClassicMancalaSetup.STORES
                        [(turnState.getWinnerIndex() + 1) % ClassicMancalaSetup.PLAYERS]]);
    }

    private String build(Object... s) {
        StringBuilder builder = new StringBuilder();
        for (Object o : s) {
            builder.append(o);
        }
        return builder.toString();
    }
}

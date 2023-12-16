package mancala.game.logic.handler;


import mancala.game.logic.setup.ClassicMancalaSetup;
import mancala.game.logic.state.GameState;
import mancala.game.logic.state.TurnState;

/**
 * Created by Alexander on 12/11/2023
 */
public class TipHandler implements ITipHandler {

    @Override
    public String getTip(TurnState turnState, GameState state) {
        String player = state.players().get(turnState.playerIndex());
        return build("Turn [", state.turnNumber(), "], Active:" , player);
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

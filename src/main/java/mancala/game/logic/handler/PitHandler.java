package mancala.game.logic.handler;

import mancala.game.logic.handler.rules.IRulesHandler;
import mancala.game.logic.setup.MancalaSetup;
import mancala.game.logic.state.TurnState;
import org.springframework.beans.factory.annotation.Autowired;

import static mancala.game.logic.state.TurnState.StateType.*;

/**
 * Created by Alexander on 12/11/2023
 */
//composite of rules?
public class PitHandler {

    @Autowired
    MancalaSetup setup;

    @Autowired
    OutcomeHandler outcomeHandler;

    @Autowired
    IRulesHandler rulesHandler;

    public TurnState placeStone(TurnState state) {
        if (state.inHand() == 1) {
            TurnState exitRuleState = rulesHandler.checkFinalStoneRules(state);
           if (exitRuleState!=null)
               return checkGameOver(exitRuleState);
        }
        state = checkGameOver(state);

        if (state.type()== GAME_OVER)
            return state;

        int index = state.pitIndex();

        if (setup.isStoreIndex(index)) {
            if (!setup.isThisPlayerStoreIndex(index, state.playerIndex())) {
                index = nextPit(index); //ignore opponents' stores
            }
            // else will just put into player's store
        }

        //TODO CLONE? Can this lead to side-effect?
        // int[] pits = Arrays.copyOf(state.pits(), state.pits().length);
        state.pits()[index]++;
        int stonesLeft = state.inHand() - 1;
        if (stonesLeft == 0) {
            return createPlayerEndTurnState(stonesLeft, state, index);
        }

        return placeStone(
                createNormalTurnState(stonesLeft, state, index));
    }

    private TurnState checkGameOver(TurnState state) {
        int winner = outcomeHandler.getWinner(state);
        if (winner > 0) {
            return createGameOverState(0, state, winner);
        } else
            return state;
    }

    private TurnState createPlayerEndTurnState(int stonesLeft, TurnState state, int pitIndex) {
        return createNewTurnState(stonesLeft, state, pitIndex, NORMAL);
    }

    private TurnState createNormalTurnState(int stonesLeft, TurnState state, int pitIndex) {
        return createNewTurnState(stonesLeft, state, pitIndex, PLAYER_DONE);
    }

    private TurnState createGameOverState(int stonesLeft, TurnState state, int winner) {
        return createNewTurnState(stonesLeft, state, winner, GAME_OVER);
    }

    private TurnState createNewTurnState(int stonesLeft, TurnState state, int pitIndex, TurnState.StateType type) {
        return new TurnState(
                state.pits(), stonesLeft, state.playerIndex(),
                nextPit(pitIndex), type);
    }

    private int nextPit(int pitIndex) {
        return (pitIndex + 1) % setup.pitsTotal();
    }
}

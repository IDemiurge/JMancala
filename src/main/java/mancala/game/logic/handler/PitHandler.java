package mancala.game.logic.handler;

import mancala.game.logic.setup.MancalaSetup;
import mancala.game.logic.state.turn.ITurnState;
import mancala.game.logic.state.turn.TurnState;
import org.springframework.beans.factory.annotation.Autowired;

import static mancala.game.logic.state.turn.TurnState.StateType.*;

/**
 * Created by Alexander on 12/11/2023
 */
//composite of rules?
public class PitHandler {

    @Autowired
    MancalaSetup setup;

    @Autowired
    OutcomeHandler outcomeHandler;

    //recursive with exceptions? is this SOUND and SOLID?
    //ALT:  while (stones > 0 )
    public ITurnState placeStone(TurnState state) {
        if (state.inHand() == 1) {

            //end turn
            //check extra turn
            //check capture

            // captureRule.check(state);
        } else {
            int winner = outcomeHandler.getWinner(state);
            if (winner > 0) {
                setup.setWinner(winner);
                return createGameOverState(0, state, 0);
            }
        }

        //TODO CLONE? Can this lead to side-effect?
        int index = state.pitIndex();

        //use setup to check!
        if (setup.isStoreIndex(index) ) {
            if (setup.isPlayerStoreIndex(index)) {
                //store
            } else {
                index= nextPit(index); //ignore opponents' stores
            }

        }

        state.pits()[index]++;
        int stonesLeft = state.inHand() - 1;
        if (stonesLeft == 0) {
            return createPlayerEndTurnState(stonesLeft, state, index);
        }

        return placeStone(
                createNormalTurnState(stonesLeft, state, index));
    }
    //TODO
    //ALT - enum? state_type?

    private TurnState createPlayerEndTurnState(int stonesLeft, TurnState state, int pitIndex) {
        return createNewTurnState(stonesLeft, state, pitIndex, NORMAL);
    }

    private TurnState createNormalTurnState(int stonesLeft, TurnState state, int pitIndex) {
        return createNewTurnState(stonesLeft, state, pitIndex, PLAYER_DONE);
    }
    private TurnState createGameOverState(int stonesLeft, TurnState state, int pitIndex) {
        return createNewTurnState(stonesLeft, state, pitIndex, GAME_OVER);
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

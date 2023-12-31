package mancala.engine.logic.handler;

import mancala.engine.logic.rules.IRulesHandler;
import mancala.engine.logic.setup.MancalaSetup;
import mancala.engine.logic.state.GameState;
import mancala.engine.logic.state.TurnState;
import org.springframework.beans.factory.annotation.Autowired;

import static mancala.engine.logic.state.TurnState.StateType.*;

/**
 *
 */
public class PitHandler implements IPitHandler{

    @Autowired
    private MancalaSetup setup;

    @Autowired
    private IOutcomeHandler outcomeHandler;

    @Autowired
    private IRulesHandler rulesHandler;

    public PitHandler(MancalaSetup setup, IOutcomeHandler outcomeHandler, IRulesHandler rulesHandler) {
        this.setup = setup;
        this.outcomeHandler = outcomeHandler;
        this.rulesHandler = rulesHandler;
    }

    @Override
    public TurnState moveStones(GameState state, int pitIndex) {
        return checkGameOver(stepPlaceStone(createStartTurnState(state, pitIndex)));
    }

    private TurnState stepPlaceStone(TurnState state) {
        if (state.inHand() == 1) {
            TurnState exitRuleState = rulesHandler.checkFinalStoneRules(state);
            if (exitRuleState != null)
                return exitRuleState;
        }

        int pitIndex = state.pitIndex();

        if (setup.isStoreIndex(pitIndex)) {
            if (!setup.isThisPlayerStoreIndex(pitIndex, state.playerIndex())) {
                pitIndex = nextPit(pitIndex); //ignore opponents' stores
            }
            // else will just put into player's store
        }
        state = state.addStone(pitIndex);
        int stonesLeft = state.inHand() - 1;
        if (stonesLeft == 0) {
            return createPlayerEndTurnState(stonesLeft, state, pitIndex);
        }
        pitIndex = nextPit(pitIndex);
        return stepPlaceStone(
                createNormalTurnState(stonesLeft, state, pitIndex));
    }

    private TurnState checkGameOver(TurnState state) {
        int winner = outcomeHandler.getWinner(state);
        if (winner >= 0) {
            return createGameOverState(0, state, winner);
        } else
            return state;
    }

    //TODO refactor this elsewhere?

    private TurnState createStartTurnState(GameState state, int pitIndex) {
        state = state.clone();
        int inHand = state.pits()[pitIndex];
        state.pits()[pitIndex] = 0;
        return new TurnState(state.pits(), inHand, state.currentPlayer(), ++pitIndex, NORMAL, state.identifier());
    }

    private TurnState createPlayerEndTurnState(int stonesLeft, TurnState state, int pitIndex) {
        return createNewTurnState(stonesLeft, state, pitIndex, PLAYER_DONE);
    }

    private TurnState createNormalTurnState(int stonesLeft, TurnState state, int pitIndex) {
        return createNewTurnState(stonesLeft, state, pitIndex, NORMAL);
    }

    private TurnState createGameOverState(int stonesLeft, TurnState state, int winner) {
        return createNewTurnState(stonesLeft, state, winner, GAME_OVER);
    }

    private TurnState createNewTurnState(int stonesLeft, TurnState state, int pitIndex, TurnState.StateType type) {
        return new TurnState(
                state.pits(), stonesLeft, state.playerIndex(),
                pitIndex, type, state.gameIdentifier());
    }

    private int nextPit(int pitIndex) {
        return (pitIndex + 1) % setup.pitsTotal();
    }

}

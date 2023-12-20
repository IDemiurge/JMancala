package mancala.engine.logic.handler;

import mancala.engine.logic.setup.MancalaSetup;
import mancala.engine.logic.state.TurnState;

/**
 *
 */
public class OutcomeHandler implements IOutcomeHandler {
    private final MancalaSetup setup;

    public OutcomeHandler(MancalaSetup setup) {
        this.setup = setup;
    }

    @Override
    public int getWinner(TurnState state) {
        for (int i = 0; i < setup.numberOfPlayers(); i++) {
            if (checkPlayerPits(state, i * setup.pitsPerPlayer(), (i + 1) * setup.pitsPerPlayer()
                    , i //account for stores
            )) {
                return i;
            }
        }
        return -1;
    }

    private boolean checkPlayerPits(TurnState state, int start, int end, int offset) {
        for (int i = start; i < end; i++) {
            if (state.pits()[i + offset] == 0) {
                continue;
            }
            return false;
        }
        return true;
    }
}

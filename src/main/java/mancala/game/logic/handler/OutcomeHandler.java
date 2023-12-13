package mancala.game.logic.handler;

import mancala.game.logic.setup.MancalaSetup;
import mancala.game.logic.state.TurnState;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Alexander on 12/12/2023
 */
public class OutcomeHandler {
    @Autowired
    MancalaSetup setup;

    //is this a natural way to do this? game can end by capture..?
    public int getWinner(TurnState state) {
        for (int i = 0; i < setup.playersNumber(); i++) {
            if (checkPlayerPits(state, i * setup.pitsPerPlayer(), (i + 1) * setup.pitsPerPlayer()
            , i //account for stores
            )) {
                return i;
            }
        }
        return -1;
    }

    public boolean checkPlayerPits(TurnState state, int start, int end, int offset) {
        for (int i = start; i < end; i++) {
            if (state.pits()[i+offset] == 0) {
                continue;
            }
            return false;
        }
        return true;
    }
}

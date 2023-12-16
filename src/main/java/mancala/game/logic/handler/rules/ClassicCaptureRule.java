package mancala.game.logic.handler.rules;

import lombok.extern.slf4j.Slf4j;
import mancala.game.logic.setup.ClassicMancalaSetup;
import mancala.game.logic.state.TurnState;
import mancala.game.utils.MancalaMathUtils;

@Slf4j
public class ClassicCaptureRule implements CaptureRule {
    @Override
    public TurnState enact(TurnState state) {
        TurnState clone = state.clone();
        int index = MancalaMathUtils.getOppositePit(ClassicMancalaSetup.PITS_PER_PLAYER,
                state.pitIndex(), state.playerIndex());
        int captured = clone.pits()[index];
        //TODO add gameId to states -> map<id, list<players>>
        log.info("Captured " + captured + " stones from " + index);
        clone.pits()[index] = 0;
        clone.pits()[ClassicMancalaSetup.STORES[clone.playerIndex()]] += captured;
        return state;
    }

    @Override
    public boolean check(TurnState state) {
        if (Math.abs(state.pitIndex() - ClassicMancalaSetup.STORES[state.playerIndex()]) > ClassicMancalaSetup.PITS_PER_PLAYER)
            return false; //not player's own pit
        if (ClassicMancalaSetup.STORES[0] == state.pitIndex() || ClassicMancalaSetup.STORES[1] == state.pitIndex())
            return false; //not for stores
        return state.pits()[state.pitIndex()] == 0;
    }
}

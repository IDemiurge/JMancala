package mancala.game.logic.handler.rules;

import lombok.extern.slf4j.Slf4j;
import mancala.game.logic.setup.ClassicMancalaSetup;
import mancala.game.logic.state.TurnState;
import mancala.game.utils.MancalaMathUtils;

/**
 * Created by Alexander on 12/13/2023
 */
@Slf4j
public class ClassicExtraTurnRule implements ExtraTurnRule {
    @Override
    public TurnState enact(TurnState state) {
        log.info("Extra turnNumber for player #"+state.playerIndex());
        return state.addStone(state.pitIndex());
    }

    @Override
    public boolean check(TurnState state) {
        return state.pitIndex() == MancalaMathUtils.getPlayerStoreIndex(ClassicMancalaSetup.PITS_PER_PLAYER, state.playerIndex());
    }
}

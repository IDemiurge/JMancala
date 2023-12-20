package mancala.engine.logic.rules;

import lombok.extern.slf4j.Slf4j;
import mancala.engine.logic.setup.ClassicMancalaSetup;
import mancala.engine.logic.setup.MancalaSetup;
import mancala.engine.logic.state.TurnState;
import mancala.common.utils.MancalaMathUtils;

/**
 * Created by Alexander on 12/13/2023
 */
@Slf4j
public class ClassicExtraTurnRule implements ExtraTurnRule {

    private final MancalaSetup setup;

    public ClassicExtraTurnRule(MancalaSetup setup) {
        this.setup = setup;
    }
    @Override
    public TurnState enact(TurnState state) {
        log.info("Extra turn for player #"+state.playerIndex());
        return state.addStone(state.pitIndex());
    }

    @Override
    public boolean check(TurnState state) {
        return state.pitIndex() == MancalaMathUtils.getPlayerStoreIndex(ClassicMancalaSetup.PITS_PER_PLAYER, state.playerIndex());
    }
}

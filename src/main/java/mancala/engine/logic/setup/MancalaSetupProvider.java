package mancala.engine.logic.setup;

import mancala.common.MancalaGameMode;
import mancala.engine.logic.handler.ITipHandler;
import mancala.engine.logic.handler.OutcomeHandler;
import mancala.engine.logic.handler.PitHandler;
import mancala.engine.logic.handler.TipHandler;
import mancala.engine.logic.rules.ClassicRulesHandler;
import mancala.engine.logic.rules.IRulesHandler;

/**
 * Created by Alexander on 12/18/2023
 */
public class MancalaSetupProvider implements IMancalaSetupProvider {
    @Override
    public MancalaSetup createSetup(MancalaGameMode mode) {
        return switch (mode) {
            case Classic -> new ClassicMancalaSetup();
            case Kalaha -> null; //TODO
        };
    }

    @Override
    public PitHandler createPitHandler(MancalaGameMode mode) {
        return new PitHandler(createSetup(mode), createOutcomeHandler(mode), createRulesHandler(mode));
    }

    private IRulesHandler createRulesHandler(MancalaGameMode mode) {
        MancalaSetup setup = createSetup(mode);
        return switch (mode) {
            case Classic -> {
                yield new ClassicRulesHandler(setup);
            }
            case Kalaha -> null; //TODO
        };
    }

    private OutcomeHandler createOutcomeHandler(MancalaGameMode mode) {
        return new OutcomeHandler(createSetup(mode));
    }

    @Override
    public ITipHandler createTipHandler(MancalaGameMode mode) {
        return new TipHandler();
    }
}

package mancala.engine.logic.setup;

import mancala.common.MancalaGameMode;
import mancala.engine.logic.handler.*;
import mancala.engine.logic.rules.ClassicRulesHandler;
import mancala.engine.logic.rules.IRulesHandler;

/**
 * 
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
    public IPitHandler createPitHandler(MancalaGameMode mode) {
        return new PitHandler(createSetup(mode), createOutcomeHandler(mode), createRulesHandler(mode));
    }

    private IRulesHandler createRulesHandler(MancalaGameMode mode) {
        MancalaSetup setup = createSetup(mode);
        return switch (mode) {
            case Classic -> new ClassicRulesHandler(setup);
            case Kalaha -> null; //TODO
        };
    }

    private IOutcomeHandler createOutcomeHandler(MancalaGameMode mode) {
        return new OutcomeHandler(createSetup(mode));
    }

    @Override
    public ITipHandler createTipHandler(MancalaGameMode mode) {
        return new TipHandler();
    }
}

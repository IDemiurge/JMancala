package mancala.game.logic.setup;

import mancala.game.logic.handler.ITipHandler;
import mancala.game.logic.handler.OutcomeHandler;
import mancala.game.logic.handler.PitHandler;
import mancala.game.logic.handler.TipHandler;
import mancala.game.logic.handler.rules.ClassicRulesHandler;
import mancala.game.logic.handler.rules.IRulesHandler;

/**
 * Created by Alexander on 12/18/2023
 */
public class MancalaSetupProvider implements IMancalaSetupProvider{
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
      return switch (mode) {
            case Classic -> new ClassicRulesHandler();
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

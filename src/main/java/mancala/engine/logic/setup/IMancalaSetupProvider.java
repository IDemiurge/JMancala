package mancala.engine.logic.setup;

import mancala.common.MancalaGameMode;
import mancala.engine.logic.handler.ITipHandler;
import mancala.engine.logic.handler.PitHandler;

public interface IMancalaSetupProvider {
    MancalaSetup createSetup(MancalaGameMode mode);

    PitHandler createPitHandler(MancalaGameMode mode);

    ITipHandler createTipHandler(MancalaGameMode mode);
}

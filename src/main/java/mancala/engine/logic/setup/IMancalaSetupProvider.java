package mancala.engine.logic.setup;

import mancala.common.MancalaGameMode;
import mancala.engine.logic.handler.IPitHandler;
import mancala.engine.logic.handler.ITipHandler;

public interface IMancalaSetupProvider {
    MancalaSetup createSetup(MancalaGameMode mode);

    IPitHandler createPitHandler(MancalaGameMode mode);

    ITipHandler createTipHandler(MancalaGameMode mode);
}

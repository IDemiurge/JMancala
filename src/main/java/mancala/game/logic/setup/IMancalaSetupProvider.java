package mancala.game.logic.setup;

import mancala.game.logic.handler.ITipHandler;
import mancala.game.logic.handler.PitHandler;

public interface IMancalaSetupProvider {
    MancalaSetup createSetup(MancalaGameMode mode);

    PitHandler createPitHandler(MancalaGameMode mode);

    ITipHandler createTipHandler(MancalaGameMode mode);
}

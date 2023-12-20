package mancala.engine.logic.setup;

import mancala.common.MancalaGameMode;

import java.util.Set;

public record GameSetupData(String identifier, Set<String> players, MancalaGameMode mode) {
}

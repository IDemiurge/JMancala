package mancala.game;

import mancala.game.logic.setup.MancalaGameMode;

import java.util.Set;

public record GameSetupData(String identifier, Set<String> players, MancalaGameMode mode) {
}

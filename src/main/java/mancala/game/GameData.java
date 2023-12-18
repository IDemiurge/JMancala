package mancala.game;

import mancala.game.logic.setup.MancalaGameMode;

import java.util.List;

public record GameData(String identifier, List<String> players, MancalaGameMode mode) {
}

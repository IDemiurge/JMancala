package mancala.game.logic.state;

import java.util.ArrayList;
import java.util.List;

public record GameState(int turnNumber, int[] pits, int currentPlayer, String statusMessage, List<String> players, String identifier,
                        boolean gameOver) {

    public GameState clone() {
        return new GameState(turnNumber, pits, currentPlayer, statusMessage, players, identifier, gameOver);
    }

    public GameState addPlayer(String player) {
        List<String> players = new ArrayList<>(players());
        players.add(player);
        return new GameState(turnNumber, pits, currentPlayer, statusMessage, players, identifier, gameOver);
    }

    public String currentPlayerName() {
        return players().get(currentPlayer);
    }

    public String getWinnerName(TurnState turnState) {
        if (turnState.type() == TurnState.StateType.GAME_OVER) {
            return players().get(turnState.getWinnerIndex());
        }
        return null;
    }

}


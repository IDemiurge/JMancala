package mancala.game.logic.state;

import java.util.ArrayList;
import java.util.List;

public record GameState(int[] pits, int currentPlayer, String statusMessage, List<String> players, String identifier) {

    public GameState clone(){
        return new GameState(pits, currentPlayer, statusMessage, players, identifier);
    }
    public GameState addPlayer(String player){
        List<String> players  = new ArrayList<>(players());
        players.add(player);
        return new GameState(pits, currentPlayer, statusMessage, players, identifier);
    }

    public String currentPlayerName() {
        return players().get(currentPlayer);
    }
}


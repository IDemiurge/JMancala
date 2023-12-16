package mancala.game.logic.state;

import java.util.ArrayList;
import java.util.List;

public record GameState(int turnNumber, int[] pits, int currentPlayer, String statusMessage, List<String> players,
                        String identifier,
                        String victor) {

    public static Builder builder() {
        return new Builder();
    }

    public GameState clone() {
        return new GameState(turnNumber, pits, currentPlayer, statusMessage, players, identifier, victor);
    }

    public GameState addPlayer(String player) {
        List<String> players = new ArrayList<>(players());
        players.add(player);
        return new GameState(turnNumber, pits, currentPlayer, statusMessage, players, identifier, victor);
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

    public boolean gameOver() {
        return victor !=null && !victor.isEmpty();
    }

    public static class Builder {
        private int turnNumber;
        private int[] pits;
        private int currentPlayer;
        private String statusMessage;
        private List<String> players;
        private String identifier;
        private String victor;

        public Builder turnNumber(int turnNumber) {
            this.turnNumber = turnNumber;
            return this;
        }

        public Builder pits(int[] pits) {
            this.pits = pits;
            return this;
        }

        public Builder currentPlayer(int currentPlayer) {
            this.currentPlayer = currentPlayer;
            return this;
        }

        public Builder statusMessage(String statusMessage) {
            this.statusMessage = statusMessage;
            return this;
        }

        public Builder players(List<String> players) {
            this.players = players;
            return this;
        }

        public Builder identifier(String identifier) {
            this.identifier = identifier;
            return this;
        }

        public Builder victor(String victor) {
            this.victor = victor;
            return this;
        }

        public Builder copyFields(GameState state) {
            return pits(state.pits())
                    .turnNumber(state.turnNumber())
                    .currentPlayer(state.currentPlayer())
                    .statusMessage(state.statusMessage())
                    .players(state.players())
                    .identifier(state.identifier())
                    .victor(state.victor());
        }

        public GameState build() {
            return new GameState(turnNumber, pits, currentPlayer, statusMessage, players, identifier, victor);
        }

    }
}


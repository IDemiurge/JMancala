package mancala.engine.logic.state;

import mancala.engine.logic.handler.GameHandler;

import java.util.List;

/**
 * Created by Alexander on 12/20/2023
 */
public class GameStateBuilder {
    private int turnNumber;
    private int[] pits;
    private int currentPlayer;
    private String statusMessage;
    private List<String> players;
    private String identifier;
    private String victor;

    public GameStateBuilder turnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
        return this;
    }

    public GameStateBuilder pits(int[] pits) {
        this.pits = pits;
        return this;
    }

    public GameStateBuilder currentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
        return this;
    }

    public GameStateBuilder statusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
        return this;
    }

    public GameStateBuilder players(List<String> players) {
        this.players = players;
        return this;
    }

    public GameStateBuilder identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public GameStateBuilder victor(String victor) {
        this.victor = victor;
        return this;
    }

    public GameStateBuilder copyFields(GameState state) {
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

    public int getTurnNumber() {
        return turnNumber;
    }

    public List<String> getPlayers() {
        return players;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }


    public static GameStateBuilder compose(GameHandler handler, TurnState turnState, GameState state) {
        int currentPlayer = turnState.playerIndex();
        int turn = state.turnNumber();
        GameStateBuilder builder = GameState.builder()
                .copyFields(state)
                .pits(turnState.pits());

        switch (turnState.type()) {
            case PLAYER_DONE:
                if (turnState.playerIndex() == 0) {
                    turn++;  //increment turnNumber only when host moves
                }
                currentPlayer = handler.nextPlayer(turnState.playerIndex());
            case NORMAL:
                return builder
                        .currentPlayer(currentPlayer)
                        .turnNumber(turn);
            case GAME_OVER:
                return builder
                        .currentPlayer(currentPlayer)
                        .turnNumber(turn)
                        .victor(state.players().get(turnState.getWinnerIndex()));
        }
        throw new RuntimeException("Unknown state type: " + turnState.type());
    }
}

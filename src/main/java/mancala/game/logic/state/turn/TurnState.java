package mancala.game.logic.state.turn;

public record TurnState(int[] pits, int inHand, int playerIndex, int pitIndex, StateType type) implements ITurnState {
    public enum StateType {
        PLAYER_DONE,
        GAME_OVER,
        NORMAL,
    }
}

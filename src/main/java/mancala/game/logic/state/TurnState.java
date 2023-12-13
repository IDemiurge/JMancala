package mancala.game.logic.state;

public record TurnState(int[] pits, int inHand, int playerIndex, int pitIndex, StateType type)  {
    public enum StateType {
        PLAYER_DONE,
        GAME_OVER,
        NORMAL,
    }
}

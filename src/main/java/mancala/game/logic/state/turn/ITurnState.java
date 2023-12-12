package mancala.game.logic.state.turn;

public interface ITurnState {
    TurnState.StateType type();

    int[] pits();

    int inHand();

    int pitIndex();

    int playerIndex();
}

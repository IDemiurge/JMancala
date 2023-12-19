package mancala.game.logic.state;

public record TurnState(int[] pits, int inHand, int playerIndex, int pitIndex, StateType type) {

    public TurnState addStone(int pitIndex) {
        TurnState clone = clone();
        clone.pits[pitIndex]++;
        return clone;
    }

    public enum StateType {
        PLAYER_DONE,
        GAME_OVER,
        NORMAL,
    }

    public TurnState clone() {
        return new TurnState(pits, inHand, playerIndex, pitIndex, type);
    }

    /**
     * @return
     */
    public int getWinnerIndex() {
        if (type == StateType.GAME_OVER) {
            return pitIndex();
        }
        return -1;
    }
}

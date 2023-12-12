package mancala.game.logic.setup;

public interface MancalaSetup {
    int[] startingPits();

    int playersNumber();

    int stonesPerPit();

    int pitsPerPlayer();

    void setPlayerIndex(int playerIndex);

    //TODO important for real-world use
    int playerIndex();

    boolean isStoreIndex(int index);

    boolean isPlayerStoreIndex(int index);

    int pitsTotal();

    void setWinner(int winner);

    int winner();
}

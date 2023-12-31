package mancala.engine.logic.setup;

public interface MancalaSetup {
    int[] startingPits();

    int[] stores();

    //TODO this is both the minimum and maximum number of players, could be more flexible?
    int numberOfPlayers();

    int stonesPerPit();

    int pitsPerPlayer();

    boolean isStoreIndex(int index);

    boolean isThisPlayerStoreIndex(int index, int i);

    int pitsTotal();

    boolean isPlayersPit(int playerIndex, int pitIndex);
}

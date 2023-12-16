package mancala.game.logic.setup;

public interface MancalaSetup {
    int[] startingPits();

    //TODO this is both the minimum and maximum number of players, could be more flexible?
    int playersNumber();

    int stonesPerPit();

    int pitsPerPlayer();

    boolean isStoreIndex(int index);

    boolean isThisPlayerStoreIndex(int index, int i);

    int pitsTotal();

    String getName();
}

package mancala.engine.logic.setup;

import java.util.Arrays;

/**
 * 
 */
public class ClassicMancalaSetup implements MancalaSetup {
    public static final int STONES_PER_PIT = 6;
    public static final int PITS_PER_PLAYER = 6;
    public static final int PLAYERS = 2;
    public static final int PITS_TOTAL = PLAYERS + PLAYERS * PITS_PER_PLAYER;
    public static final int[] STORES = new int[]{6, 13};
    public static final int TOTAL_STONES = PITS_PER_PLAYER * PLAYERS * STONES_PER_PIT;

    @Override
    public int[] startingPits() {
        int[] pits = new int[numberOfPlayers() + pitsPerPlayer() * numberOfPlayers()];
        Arrays.fill(pits, stonesPerPit());
        for (int i = 0; i < numberOfPlayers(); i++) {
            pits[i + (1 + i) * pitsPerPlayer()] = 0;
        }
        return pits;
    }

    @Override
    public boolean isStoreIndex(int index) {
        for (int i = 0; i < numberOfPlayers(); i++) {
            if (isThisPlayerStoreIndex(index, i)) {
                return true;
            }
        }
        return false;
    }

    public boolean isThisPlayerStoreIndex(int index, int playerIndex) {
        if (playerIndex >= PLAYERS){
            throw new IllegalArgumentException("playerIndex >= 2, not supported for Classic Mancala");
        }
        return index == STORES[playerIndex];
    }

    @Override
    public int[] stores() {
        return STORES;
    }

    @Override
    public int numberOfPlayers() {
        return PLAYERS;
    }

    @Override
    public int stonesPerPit() {
        return STONES_PER_PIT;
    }

    @Override
    public int pitsPerPlayer() {
        return PITS_PER_PLAYER;
    }

    @Override
    public int pitsTotal() {
        return PITS_TOTAL;
    }

    @Override
    public boolean isPlayersPit(int playerIndex, int pitIndex) {
        if (playerIndex==0) {
            if (pitIndex>5) {
                return false;
            }
        }
        if (playerIndex==1) {
            if (pitIndex<7) {
                return false;
            }
        }
        return true;
    }
}

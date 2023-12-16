package mancala.game.logic.setup;

import java.util.Arrays;

/**
 * Created by Alexander on 12/11/2023
 */
public class ClassicMancalaSetup implements MancalaSetup {
    public static final int STONES_PER_PIT = 6;
    public static final int PITS_PER_PLAYER = 6;
    public static final int PLAYERS = 2;
    public static final int PITS_TOTAL = PLAYERS + PLAYERS * PITS_PER_PLAYER;
    public static final int[] STORES = new int[]{6, 13};

    @Override
    public int[] startingPits() {
        int[] pits = new int[playersNumber() + pitsPerPlayer() * playersNumber()];
        Arrays.fill(pits, stonesPerPit());
        for (int i = 0; i < playersNumber(); i++) {
            pits[i + (1 + i) * pitsPerPlayer()] = 0;
        }
        return pits;
    }

    @Override
    public boolean isStoreIndex(int index) {
        for (int i = 0; i < playersNumber(); i++) {
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
    public int playersNumber() {
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
    public String getName() {
        return "Classic";
    }
}

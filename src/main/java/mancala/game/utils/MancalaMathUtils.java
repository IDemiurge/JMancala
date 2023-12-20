package mancala.game.utils;

import mancala.game.logic.setup.MancalaSetup;

/**
 * Created by Alexander on 12/13/2023
 */
public class MancalaMathUtils {

    public static final int getOppositePit(int pitsPerPlayer, int index, int playerIndex) {
        if (index == pitsPerPlayer || index == pitsPerPlayer * 2 + 1)
            throw new RuntimeException("Opposite pit calculation won't work for store pit at " + index);

        int dstToStore = Math.abs(pitsPerPlayer - index);
        int diff = dstToStore * 2 + 2;
        return playerIndex == 0 ?  diff - index : index - diff;

    }

    public static int getPlayerStoreIndex(int pitsPerPlayer, int playerIndex) {
        return pitsPerPlayer * (playerIndex + 1) + playerIndex;
    }

    public static int translateToPlayerPitIndex(int pitIndex, int currentPlayer, MancalaSetup setup) {
        return (pitIndex-currentPlayer)%setup.pitsPerPlayer();
    }
}

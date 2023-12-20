package mancala.common.utils;

import mancala.common.exception.MathException;
import mancala.engine.logic.setup.MancalaSetup;

import java.util.Arrays;

/**
 * Created by Alexander on 12/13/2023
 */
public class MancalaMathUtils {

    public static final int getOppositePit(int pitsPerPlayer, int index, int playerIndex, int pitsTotal) {
        if (index == pitsPerPlayer || index == pitsPerPlayer * 2 + 1)
            throw new IllegalArgumentException("Opposite pit calculation won't work for store pit at " + index);

        int dstToStore = Math.abs(pitsPerPlayer - index);
        int diff = dstToStore * 2 + 2;
        int opposite = (playerIndex == 0 ? diff - index : index - diff);
        if (opposite < 0 || opposite >= pitsTotal){
            throw new MathException("Opposite pit calculation produced wrong result "+ opposite
                    +
                    "index: " +index+
                    "playerIndex: " +playerIndex+
                    "pitsPerPlayer: " +pitsPerPlayer);
        }
        return opposite;

    }

    public static int getPlayerStoreIndex(int pitsPerPlayer, int playerIndex) {
        return pitsPerPlayer * (playerIndex + 1) + playerIndex;
    }

    public static int translateToPlayerPitIndex(int pitIndex, int currentPlayer, MancalaSetup setup) {
        return (pitIndex-currentPlayer)%setup.pitsPerPlayer();
    }

}

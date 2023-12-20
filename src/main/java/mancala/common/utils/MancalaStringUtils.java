package mancala.common.utils;

import mancala.engine.logic.setup.ClassicMancalaSetup;
import mancala.engine.logic.setup.MancalaSetup;

/**
 * Created by Alexander on 12/14/2023
 */
public class MancalaStringUtils {
    public static String prettyPits(int[] pits) {
        return prettyPits(pits, new ClassicMancalaSetup());
    }
    public static String prettyPits(int[] pits, MancalaSetup setup) {
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (int player = 1; player <= 2; player++) {
            int limit = i + setup.pitsPerPlayer();
            builder.append("\nPlayer " + player);
            for (; i < limit; i++) {
                builder.append("\nPit #"+ (1+i-(player-1)-((player-1)*setup.pitsPerPlayer()))+": " + pits[i]);
            }
            builder.append("\nStore: " + pits[i++]); //store
        }
        return builder.toString();
    }
}

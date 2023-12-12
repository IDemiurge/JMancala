package mancala.game.logic.setup;

import java.util.Arrays;

/**
 * Created by Alexander on 12/11/2023
 */
public class ClassicMancalaSetup implements MancalaSetup {

    private int winner = -1;

    @Override
        public int[] startingPits() {
        int[] pits = new int[playersNumber()+ pitsPerPlayer()*playersNumber()];
        Arrays.fill(pits, stonesPerPit());
        for (int i = 0; i < playersNumber(); i++) {
            pits[i+(1+i)*pitsPerPlayer()] = 0;
        }
        return pits;
    }
    @Override
    public boolean isStoreIndex(int index) {
        for (int i = 0; i < playersNumber(); i++) {
            if (isThisPlayerStoreIndex(index, i))
                return true;
        }
        return false;
    }


    //6 13 20 ...
    public boolean isThisPlayerStoreIndex(int index, int playerIndex) {
        if (playerIndex> pitsPerPlayer()){
            //TODO
        }
        return index % pitsPerPlayer() == playerIndex;
    }

    @Override
    public int playersNumber() {
        return 2;
    }

    @Override
    public int stonesPerPit() {
        return 6;
    }

    @Override
    public int pitsPerPlayer() {
        return 6;
    }

    @Override
    public int pitsTotal() {
        return playersNumber()+playersNumber()*pitsPerPlayer();
    }

    @Override
    public void setWinner(int winner) {
        this.winner = winner;
    }

    @Override
    public int winner() {
        return this.winner;
    }


}

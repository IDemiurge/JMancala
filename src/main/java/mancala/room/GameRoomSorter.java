package mancala.room;

import java.util.Comparator;

/**
 * Created by Alexander on 12/17/2023
 */
public class GameRoomSorter implements Comparator<GameRoom> {
    @Override
    public int compare(GameRoom o1, GameRoom o2) {
        //TODO status? created at?
        if (o1.getGameMode() != o2.getGameMode()) {
            return o1.getGameMode().abbreviation.compareTo(o2.getGameMode().abbreviation);
        }
        return o1.getHostUserName().compareTo(o2.getHostUserName());
    }
}

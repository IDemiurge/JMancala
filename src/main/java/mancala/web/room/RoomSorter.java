package mancala.web.room;

import java.util.Comparator;

/**
 * 
 */
public class RoomSorter implements Comparator<Room> {
    @Override
    public int compare(Room o1, Room o2) {
        //TODO status? created at?
        if (o1.getGameMode() != o2.getGameMode()) {
            return o1.getGameMode().abbreviation.compareTo(o2.getGameMode().abbreviation);
        }
        return o1.getHostUserName().compareTo(o2.getHostUserName());
    }
}

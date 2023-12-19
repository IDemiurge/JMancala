package mancala.game.logic.state;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander on 12/19/2023
 */
public class GameLog {
    public final List<String> messages = new ArrayList<>();

    public List<String> getMessages() {
        return messages;
    }

    public boolean add(String s) {
        return messages.add(s);
    }
}

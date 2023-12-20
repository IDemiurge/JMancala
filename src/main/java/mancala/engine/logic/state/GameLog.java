package mancala.engine.logic.state;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class GameLog {
    public final List<String> messages = new ArrayList<>();

    public List<String> getMessages() {
        return messages;
    }

    public void addMessage(String s) {
        messages.add(s);
    }
}

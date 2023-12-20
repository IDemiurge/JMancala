package mancala.web.user;

/**
 * 
 */
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class UserStore {
    private final Set<String> activeUsernames = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public boolean registerUser(String username) {
        return activeUsernames.add(username);
    }

    public void removeUser(String username) {
        activeUsernames.remove(username);
    }

    public boolean isUserActive(String username) {
        return activeUsernames.contains(username);
    }
}


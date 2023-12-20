package mancala.web.utils;

import lombok.extern.slf4j.Slf4j;
import mancala.web.render.ModelAttributes;
import org.springframework.ui.Model;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * 
 */
@Slf4j
public class SessionTools {
    public final Map<String, Map<String, String>> cache = new ConcurrentHashMap<>();
    public final Map<String, String> userCache = new ConcurrentHashMap<>();
    public final Set<String> banned = new ConcurrentSkipListSet<>();

    public void populateModel(Model model, String tabId) {
        model.addAttribute(ModelAttributes.USERNAME, getAttribute(tabId, ModelAttributes.USERNAME));
        model.addAttribute(ModelAttributes.TAB_ID, tabId);
    }

    public void setUserIdentifier(String userName, String tabId) {
        userCache.put(userName, tabId);
    }

    public void setAttributeForUser(String userName, String key, String value) {
        String tabId = userCache.get(userName);
        setAttribute(tabId, key, value);
    }

    public void setAttribute(String tabId, String key, String value) {
        cache.putIfAbsent(tabId, new ConcurrentHashMap<>());
        cache.get(tabId).put(key, value);
    }

    public String getAttribute(String tabId, String key) {
        if (cache.get(tabId) == null) {
            if (!banned.contains(tabId)) {
                log.error(tabId + " is not present in the Session Cache!");
                banned.add(tabId); //not to spam log for out-of-sync sessions
            }
            return null;
        }
        return cache.get(tabId).get(key);
    }
}

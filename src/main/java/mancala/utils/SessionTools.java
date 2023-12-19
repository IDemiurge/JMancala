package mancala.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static mancala.render.ModelAttributes.LOGIN;
import static mancala.render.ModelAttributes.TAB_ID;

/**
 * Created by Alexander on 12/18/2023
 */
@Slf4j
public class SessionTools {
    public final Map<String, Map<String, String>> cache = new ConcurrentHashMap();
    public final Map<String, String> userCache = new ConcurrentHashMap();

    public void populateModel(Model model, String tabId) {
        model.addAttribute(LOGIN, getAttribute(tabId, LOGIN));
        model.addAttribute(TAB_ID, tabId);
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
            log.error(tabId + " is not present in the Session Cache!");
        }
        return cache.get(tabId).get(key);
    }
}

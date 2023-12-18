package mancala.utils;

import jakarta.servlet.http.HttpSession;
import mancala.render.ModelAttributes;

/**
 * Created by Alexander on 12/18/2023
 */
public class SessionTools {

    public static String getGameId(HttpSession session) {
        Object id = session.getAttribute(ModelAttributes.GAME_ID);
        if (id == null) {
            //TODO
            return "";
        }
        return id.toString();
    }
}

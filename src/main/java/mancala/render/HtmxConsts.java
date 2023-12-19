package mancala.render;

/**
 * Created by Alexander on 12/18/2023
 */
public class HtmxConsts {
    public static final String START_BUTTON_ENABLED =
            """
            <form hx-post="/startGame">
                            <button type="submit">Start Game</button>
                        </form>
            """;
    public static final String START_BUTTON_DISABLED =
            """
            <form hx-post="/startGame">
                            <button type="submit" disabled>Start Game</button>
                        </form>
            """;
    public static final String START_BUTTON_ERROR =
            """
            <form>
                            <button type="submit">Start Game - ERROR</button>
                        </form>
            """;
    public static final String START_BUTTON_STARTED =
            """
            <label>Game Started</label>
            """;
}

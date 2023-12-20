package mancala.web.controller;

import mancala.common.exception.GameNotFoundException;
import mancala.common.exception.UserNotFoundException;
import mancala.common.MancalaGameMode;
import mancala.common.utils.Log;
import mancala.engine.logic.state.GameState;
import mancala.web.render.HtmxConsts;
import mancala.web.render.ModelAttributes;
import mancala.web.room.RoomService;
import mancala.web.utils.SessionTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static mancala.web.render.ModelAttributes.*;

/**
 * 
 */
@Controller
public class RoomController {

    @Autowired
    RoomService roomService;

    @Autowired
    SessionTools sessionTools;

    @PostMapping("/createGame")
    public String createGame(@RequestParam String tabId, Model model, RedirectAttributes redirectAttributes) {
        String username = sessionTools.getAttribute(tabId, USERNAME);
        if (username == null) {
            throw new UserNotFoundException(tabId);
        }
        Object gameMode = model.getAttribute(GAME_MODE);
        if (gameMode == null) {
            gameMode = MancalaGameMode.Classic;
        }
        String gameId = roomService.createNewGame(username, (MancalaGameMode) gameMode);

        redirectAttributes.addFlashAttribute(HOST_WAITING, true);
        redirectAttributes.addAttribute(TAB_ID, tabId);

        sessionTools.setAttribute(tabId, HOSTED_GAME, gameId);

        sessionTools.populateModel(model, tabId); //redundant?

        return "redirect:game/" + gameId; //to join your own game
    }

    @GetMapping("/game/{gameId}")
    public String joinGame(@RequestParam String tabId, @PathVariable String gameId, Model model) {
        //check re-join and refresh
        String username = sessionTools.getAttribute(tabId, USERNAME);
        if (username == null) {
            throw new UserNotFoundException(tabId);
        }
        int playerId = roomService.joinGame(gameId, username);

        sessionTools.setAttributeForUser(username, PLAYER_TYPE,
                playerId == 0 ? PLAYER_TYPE_HOST : PLAYER_TYPE_GUEST);
        model.addAttribute(GAME_LOG, Log.getLog(gameId).getMessages());

        sessionTools.populateModel(model, tabId);
        return "game";
    }

    @GetMapping(value = "/checkGameReadyToStart", produces = "text/html")
    @ResponseBody
    public String checkGameReadyToStart(@RequestParam("tabId") String tabId) {
        String gameId = sessionTools.getAttribute(tabId, HOSTED_GAME);
        if (gameId == null) {
            return HtmxConsts.START_BUTTON_ERROR;
        }
        if (roomService.getGameRoom(gameId).isStarted()) {
            return HtmxConsts.START_BUTTON_STARTED;
        }
        if (roomService.isReadyToStart(gameId)) {
            return HtmxConsts.START_BUTTON_ENABLED;
        }
        return HtmxConsts.START_BUTTON_DISABLED;
    }

    @GetMapping("/fetchGameRooms")
    public String syncGameRooms(@RequestParam("tabId") String tabId, Model model) {
        model.addAttribute(GAME_ROOMS, roomService.fetchGames());
        sessionTools.populateModel(model, tabId);
        return "fragments/gameRooms";
    }


    @PostMapping("/startGame")
    @ResponseBody
    public ResponseEntity startGame(@RequestParam("tabId") String tabId) {
        String gameId = sessionTools.getAttribute(tabId, HOSTED_GAME);
        if (gameId == null) {
            //TODO it's ID actually
            throw new GameNotFoundException(tabId);
        }
        GameState state = roomService.startGame(gameId);

        for (String player : state.players()) {
            sessionTools.setAttributeForUser(player, ModelAttributes.GAME_ID, gameId);
        }
        return ResponseEntity.ok("");
    }
}

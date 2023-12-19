package mancala.controller;

import mancala.game.exception.GameNotFoundException;
import mancala.game.logic.setup.MancalaGameMode;
import mancala.game.logic.state.GameState;
import mancala.render.HtmxConsts;
import mancala.render.ModelAttributes;
import mancala.room.RoomService;
import mancala.utils.SessionTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static mancala.render.ModelAttributes.*;

/**
 * Created by Alexander on 12/18/2023
 */
@Controller
public class RoomController {

    @Autowired
    RoomService roomService;

    @Autowired
    SessionTools sessionTools;

    @PostMapping("/createGame")
    public String createGame(@RequestParam String tabId, Model model, RedirectAttributes redirectAttributes) {
        Object username = sessionTools.getAttribute(tabId, USERNAME);
        if (username == null) {
            //TODO
        }
        Object gameMode = model.getAttribute(GAME_MODE);
        if (gameMode == null) {
            gameMode = MancalaGameMode.Classic;
        }
        String gameId = roomService.createNewGame(username.toString(), (MancalaGameMode) gameMode);

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
            //TODO
        }
        int playerId = roomService.joinGame(gameId, username);

        sessionTools.setAttributeForUser(username, PLAYER_TYPE,
                playerId == 0 ? PLAYER_TYPE_HOST : PLAYER_TYPE_GUEST);
        model.addAttribute(GAME_LOG, roomService.getGameRoom(gameId).getLog().getMessages());

        sessionTools.populateModel(model, tabId);
        return "game";
    }

    @GetMapping(value = "/check-game-room", produces = "text/html")
    @ResponseBody
    public String checkGameReadyToStart(@RequestParam("tabId") String tabId) {
        String gameId = sessionTools.getAttribute(tabId, HOSTED_GAME);
        if (gameId == null) {
            return HtmxConsts.START_BUTTON_ERROR;
        }
        if (roomService.isReadyToStart(gameId.toString())) {
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
        GameState state = roomService.startGame(gameId.toString());

        for (String player : state.players()) {
            sessionTools.setAttributeForUser(player, ModelAttributes.GAME_ID, gameId);
        }
        return ResponseEntity.ok("");
    }
}

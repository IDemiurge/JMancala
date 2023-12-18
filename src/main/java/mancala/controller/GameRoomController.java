package mancala.controller;

import jakarta.servlet.http.HttpSession;
import mancala.game.logic.setup.MancalaGameMode;
import mancala.render.HtmxConsts;
import mancala.render.ModelAttributes;
import mancala.room.GameRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static mancala.render.ModelAttributes.*;

/**
 * Created by Alexander on 12/18/2023
 */
@Controller
public class GameRoomController {

    @Autowired
    GameRoomService gameGameRoomService;

    @PostMapping("/createGame")
    public String createGame(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        Object username = session.getAttribute(USERNAME);
        if (username==null){
            //TODO
        }
        Object gameMode = model.getAttribute(GAME_MODE);
        if (gameMode==null){
            gameMode= MancalaGameMode.Classic;
        }
        String gameId = gameGameRoomService.createNewGame(username.toString(), (MancalaGameMode) gameMode);

        redirectAttributes.addFlashAttribute(PLAYER_TYPE, PLAYER_TYPE_HOST);
        redirectAttributes.addFlashAttribute(HOST_WAITING, true);
        session.setAttribute(HOSTED_GAME, gameId);
        return "redirect:game/"+gameId; //to join your own game
    }

    @GetMapping("/game/{gameId}")
    public String joinGame(@PathVariable String gameId, Model model, HttpSession session) {
        //check re-join and refresh
        Object username = session.getAttribute(USERNAME);
        if (username==null){
            //TODO
        }
        boolean result = gameGameRoomService.joinGame(gameId, username.toString());
        if (result){
            model.addAttribute(PLAYER_TYPE, PLAYER_TYPE_GUEST);
        } else {
            //TODO
        }
        return "game";
    }

    @GetMapping(value = "/check-game-room", produces = "text/html")
    @ResponseBody
    public String checkGameRoom(HttpSession session) {
        Object gameId = session.getAttribute(HOSTED_GAME);
        if (gameId==null){
            //TODO
            return HtmxConsts.START_BUTTON_ERROR;
        }
        if (gameGameRoomService.isReadyToStart(gameId.toString())) {
            return HtmxConsts.START_BUTTON_ENABLED;
        }
        return HtmxConsts.START_BUTTON_DISABLED;
    }

    @PostMapping("/startGame")
    @ResponseBody
    public ResponseEntity startGame(HttpSession session) {
        Object gameId = session.getAttribute(HOSTED_GAME);
        if (gameId==null){
            //TODO
        }
        gameGameRoomService.startGame(gameId.toString());
        session.setAttribute(ModelAttributes.GAME_ID, gameId);
        return ResponseEntity.ok("");
    }
}

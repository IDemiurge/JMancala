package mancala.controller;

import jakarta.servlet.http.HttpSession;
import mancala.game.logic.setup.MancalaGameMode;
import mancala.game.logic.state.GameState;
import mancala.render.HtmxConsts;
import mancala.room.GameRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import static mancala.render.ModelAttributes.*;
import static mancala.render.ModelAttributes.HOSTED_GAME;

/**
 * Created by Alexander on 12/18/2023
 */
public class GameRoomController {

    @Autowired
    GameRoomService gameGameRoomService;

    @PostMapping("/createGame")
    public String createGame(Model model, HttpSession session) {
        Object username = session.getAttribute(USERNAME);
        if (username==null){
            //TODO
        }
        Object gameMode = model.getAttribute(GAME_MODE);
        String gameId = gameGameRoomService.createNewGame(username.toString(), (MancalaGameMode) gameMode);

        model.addAttribute(PLAYER_TYPE, PLAYER_TYPE_HOST);
        return "redirect:game/"+gameId; //to join your own game
    }

    @GetMapping("/game/{gameId}")
    public String joinGame(@PathVariable String gameId, Model model, HttpSession session) {
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

    @GetMapping("/check-game-room")
    public String checkGameRoom(HttpSession session) {
        Object gameId = session.getAttribute(HOSTED_GAME);
        if (gameId==null){
            //TODO
        }
        if (gameGameRoomService.isReadyToStart(gameId.toString())) {
            return HtmxConsts.START_BUTTON_ENABLED;
        }
        return HtmxConsts.START_BUTTON_DISABLED;
    }

    @PostMapping("/startGame")
    public void startGame(Model model, HttpSession session) {
        Object gameId = session.getAttribute(HOSTED_GAME);
        if (gameId==null){
            //TODO
        }
        GameState gameState = gameGameRoomService.startGame(gameId.toString());
        model.addAttribute(GAME_STATE, gameState);
    }
}

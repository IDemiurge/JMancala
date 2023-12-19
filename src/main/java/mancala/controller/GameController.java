package mancala.controller;

import jakarta.servlet.http.HttpSession;
import mancala.game.logic.state.GameState;
import mancala.room.GameRoomService;
import mancala.utils.SessionTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static mancala.render.ModelAttributes.*;

@Controller
public class GameController {

    @Autowired
    GameRoomService gameGameRoomService;
    @Autowired
    SessionTools sessionTools;

    @PostMapping("/makeMove")

    public String makeMove(@RequestParam("tabId") String tabId, @RequestParam("pitIndex") int pitIndex, Model model, HttpSession session) {
        //TODO security - at least check if the right player is making this move
        String gameId = sessionTools.getAttribute(tabId, GAME_ID);
        GameState gameState = gameGameRoomService.makeMove(gameId, pitIndex);
        if (gameState.gameOver()) {
            //TODO properly clean up
            return "fragments/boardFragment";
        }
        model.addAttribute(GAME_STATE, gameState);
        model.addAttribute(PLAYER_TYPE, sessionTools.getAttribute(tabId, PLAYER_TYPE));
        return "fragments/boardFragment";
    }

    @GetMapping("/syncGameState")
    public String checkGameState(@RequestParam("tabId") String tabId, Model model) {
        String gameId = sessionTools.getAttribute(tabId, GAME_ID);
        if (gameId != null) {
            GameState gameState = gameGameRoomService.getGameState(gameId);

            if (gameState != null) {
                model.addAttribute(GAME_STATE, gameState);
                model.addAttribute(GAME_LOG, gameGameRoomService.getGameRoom(gameId).getLog().getMessages());
                model.addAttribute(PLAYER_TYPE, sessionTools.getAttribute(tabId, PLAYER_TYPE));
            }
        }

        return "fragments/boardFragment";
    }
}

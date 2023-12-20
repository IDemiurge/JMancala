package mancala.web.controller;

import mancala.common.utils.Log;
import mancala.engine.logic.state.GameState;
import mancala.web.room.RoomService;
import mancala.web.utils.SessionTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static mancala.web.render.ModelAttributes.*;

@Controller
public class GameController {

    @Autowired
    RoomService gameGameRoomService;
    @Autowired
    SessionTools sessionTools;

    @Autowired
    MoveValidator moveValidator;

    @PostMapping("/makeMove")
    public String makeMove(@RequestParam("tabId") String tabId, @RequestParam("pitIndex") int pitIndex, Model model) {

        String gameId = sessionTools.getAttribute(tabId, GAME_ID);
        String playerType = sessionTools.getAttribute(tabId, PLAYER_TYPE);
        String username = sessionTools.getAttribute(tabId, USERNAME);

        moveValidator.validate(username, pitIndex, gameGameRoomService.getGameRoom(gameId));

        GameState gameState = gameGameRoomService.makeMove(gameId, pitIndex);
        if (gameState.gameOver()) {
            // model.addAttribute(VICTOR, gameState.victor());
            //just display this and block board!
            //TODO properly clean up
            // gameGameRoomService.ended(gameId);
            return "fragments/boardFragment";
        }
        model.addAttribute(GAME_STATE, gameState);
        model.addAttribute(PLAYER_TYPE, playerType);
        return "fragments/boardFragment";
    }

    @GetMapping("/syncGameState")
    public String checkGameState(@RequestParam("tabId") String tabId, Model model) {
        String gameId = sessionTools.getAttribute(tabId, GAME_ID);
        if (gameId != null) {
            GameState gameState = gameGameRoomService.getGameState(gameId);
            if (gameState != null) {
                model.addAttribute(GAME_STATE, gameState);
                model.addAttribute(GAME_LOG, Log.getLog(gameId).getMessages());
                model.addAttribute(PLAYER_TYPE, sessionTools.getAttribute(tabId, PLAYER_TYPE));
            }
        }

        return "fragments/boardFragment";
    }
}

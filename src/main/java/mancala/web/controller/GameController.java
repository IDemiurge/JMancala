package mancala.web.controller;

import mancala.engine.logic.state.GameState;
import mancala.web.room.RoomService;
import mancala.web.utils.SessionTools;
import mancala.web.render.ModelAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

        String gameId = sessionTools.getAttribute(tabId, ModelAttributes.GAME_ID);
        String playerType = sessionTools.getAttribute(tabId, ModelAttributes.PLAYER_TYPE);
        String username = sessionTools.getAttribute(tabId, ModelAttributes.USERNAME);

        moveValidator.validate(username, pitIndex, gameGameRoomService.getGameRoom(gameId));

        GameState gameState = gameGameRoomService.makeMove(gameId, pitIndex);
        if (gameState.gameOver()) {
            // model.addAttribute(VICTOR, gameState.victor());
            //just display this and block board!
            //TODO properly clean up
            // gameGameRoomService.ended(gameId);
            return "fragments/boardFragment";
        }
        model.addAttribute(ModelAttributes.GAME_STATE, gameState);
        model.addAttribute(ModelAttributes.PLAYER_TYPE, playerType);
        return "fragments/boardFragment";
    }

    @GetMapping("/syncGameState")
    public String checkGameState(@RequestParam("tabId") String tabId, Model model) {
        String gameId = sessionTools.getAttribute(tabId, ModelAttributes.GAME_ID);
        if (gameId != null) {
            GameState gameState = gameGameRoomService.getGameState(gameId);
            if (gameState != null) {
                model.addAttribute(ModelAttributes.GAME_STATE, gameState);
                model.addAttribute(ModelAttributes.GAME_LOG, gameGameRoomService.getGameRoom(gameId).getLog().getMessages());
                model.addAttribute(ModelAttributes.PLAYER_TYPE, sessionTools.getAttribute(tabId, ModelAttributes.PLAYER_TYPE));
            }
        }

        return "fragments/boardFragment";
    }
}

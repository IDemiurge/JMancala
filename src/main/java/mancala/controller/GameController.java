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

import static mancala.render.ModelAttributes.GAME_STATE;

@Controller
public class GameController {

    @Autowired
    GameRoomService gameGameRoomService;

    @PostMapping("/makeMove")
    public String makeMove(@RequestParam("pitIndex") int pitIndex, Model model, HttpSession session) {
        //TODO security - at least check if the right player is making this move
        String gameId = SessionTools.getGameId(session);
        GameState gameState = gameGameRoomService.makeMove(gameId, pitIndex);
        if (gameState.gameOver()) {
            //TODO properly clean up
            return "fragments/boardFragment";
        }
        model.addAttribute(GAME_STATE, gameState);
        return "fragments/boardFragment";
    }

    @GetMapping("/syncGameState")
    public String checkGameState(Model model, HttpSession session) {
        String gameId = SessionTools.getGameId(session);
        GameState gameState = gameGameRoomService.getGameState(gameId);
        if (gameState != null) {
            model.addAttribute(GAME_STATE, gameState);
        }

        return "fragments/boardFragment";
    }
}

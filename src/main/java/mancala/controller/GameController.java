package mancala.controller;

import mancala.game.logic.state.GameState;
import mancala.room.GameRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static mancala.render.ModelAttributes.GAME_STATE;

@Controller
public class GameController {

    @Autowired
    GameRoomService gameGameRoomService;

    @PostMapping("/game/{gameId}/move")
    public String makeMove(@PathVariable String gameId, @RequestParam("pitIndex") int pitIndex, Model model) {

        GameState gameState = gameGameRoomService.makeMove(gameId, pitIndex);
        if (gameState.gameOver()){
            //properly clean up?
            return "gameBoard :: endGameFragment";
        }
        model.addAttribute(GAME_STATE, gameState);
        //insert boardFragment into gameBoard?
        return "gameBoard :: boardFragment";
    }
}

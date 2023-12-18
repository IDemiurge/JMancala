package mancala.controller;

import jakarta.servlet.http.HttpSession;
import mancala.game.logic.setup.MancalaGameMode;
import mancala.game.logic.state.GameState;
import mancala.room.GameRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static mancala.render.ModelAttributes.*;

@Controller
public class GameController {

    @Autowired
    GameRoomService gameGameRoomService;

    @PostMapping("/createGame")
    public String createGame(Model model, HttpSession session) {
        Object username = session.getAttribute(USERNAME);
        if (username==null){
            //TODO
        }
        Object gameMode = model.getAttribute(GAME_MODE);
        GameState gameState = gameGameRoomService.createNewGame(username.toString(), (MancalaGameMode) gameMode);
        model.addAttribute(GAME_STATE, gameState);
        model.addAttribute(PLAYER_TYPE, PLAYER_TYPE_HOST);
        String gameId = gameState.identifier();
        return "redirect:game/"+gameId; //to join your own game?
        //this won't work!
    }

    @GetMapping("/game/{gameId}")
    public String joinGame(@PathVariable String gameId, Model model, HttpSession session) {
        Object username = session.getAttribute(USERNAME);
        if (username==null){
            //TODO
        }
        GameState gameState = gameGameRoomService.joinGame(gameId, username.toString());
        //check if we should start the game?
        model.addAttribute(GAME_STATE, gameState);
        model.addAttribute(PLAYER_TYPE, PLAYER_TYPE_GUEST);
        return "game"; //fragment?
    }

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

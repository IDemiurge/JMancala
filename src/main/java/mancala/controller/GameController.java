package mancala.controller;

import mancala.game.IGameService;
import mancala.game.logic.state.GameState;
import mancala.session.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class GameController {

    @Autowired
    SessionService gameSessionService;

    //TODO homeController?

    // @GetMapping("/")
    // public String home() {
    //     return "index";
    // }

    @GetMapping("/createGame")
    public String createGame(Model model) {
        GameState gameState = gameSessionService.createNewGame();
        model.addAttribute("gameState", gameState);
        return "game";
    }

    @GetMapping("/joinGame/{gameId}")
    public String joinGame(@PathVariable String gameId, Model model) {
        GameState gameState = gameSessionService.joinGame(gameId);
        model.addAttribute("gameState", gameState);
        return "game";
    }
// need to differentiate between players - they must send some data about themselves in each request so we can serve
//corresponding version of the board - flipped and properly (un)blocked

    @PostMapping("/game/{gameId}/move")
    public String makeMove(@PathVariable String gameId, @RequestParam("pitIndex") int pitIndex, Model model) {
        GameState gameState = gameSessionService.makeMove(gameId, pitIndex);
        model.addAttribute("gameState", gameState);
        return "gameBoard :: boardFragment";
    }
}

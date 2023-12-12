package mancala.controller;

import mancala.game.IGameService;
import mancala.game.logic.state.GameState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    @Autowired
    private IGameService gameService; // Service to handle game logic


    @GetMapping("/host")
    public String host(Model model) {
        GameState gameState = gameService.init(0);
        model.addAttribute("gameState", gameState);
        return "gameBoard :: boardFragment"; // Returns a Thymeleaf fragment
    }
    //TODO game id / room?
    @GetMapping("/join")
    public String join(Model model) {
        GameState gameState = gameService.init(1);
        model.addAttribute("gameState", gameState);
        return "gameBoard :: boardFragment"; // Returns a Thymeleaf fragment
    }


    @PostMapping("/makeMove")
    public String makeMove(@RequestParam("pitIndex") int pitIndex, Model model) {
        GameState gameState = gameService.makeMove(pitIndex);
        model.addAttribute("gameState", gameState);
        return "gameBoard :: boardFragment"; // Returns a Thymeleaf fragment
    }
}

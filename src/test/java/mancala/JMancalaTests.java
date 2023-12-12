package mancala;

import mancala.config.MancalaConfig;
import mancala.game.IGameService;
import mancala.game.logic.state.GameState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Arrays;

/**
 * Created by Alexander on 12/11/2023
 */
@SpringBootTest(classes = MancalaConfig.class)

public class JMancalaTests {
    @Autowired
    private IGameService gameService;

    @Test
    public void testInitialGameState() {
        // Set up an initial game state
        GameState initialState = gameService.host();

        Assert.isTrue(initialState.pits().length == 14, "Should have 14 pits including stores");
        Assert.isTrue(initialState.pits()[6] == 0, "Player's store is empty initially");
        Assert.isTrue(initialState.pits()[13] == 0, "Opponent's store is empty initially");
        Assert.isTrue(Arrays.stream(initialState.pits(), 0, 6).allMatch(pit -> pit == 6), "Each player's pit should have 6 stones initially");
        Assert.isTrue(Arrays.stream(initialState.pits(), 7, 13).allMatch(pit -> pit == 6), "Each opponent's pit should have 6 stones initially");
    }

    @Test
    public void testMakeMove() {
        GameState gameState = gameService.host();
        gameService.join(gameState);

        // Make a move and get the updated state
        GameState updatedState = gameService.makeMove(gameState, 3);

        Assert.isTrue(updatedState.pits()[3] == 0, "Pit 3 should be empty after move");
        Assert.isTrue(updatedState.pits()[4] == 7, "Pit 4 should have 1 more stone after move");
        Assert.isTrue(updatedState.pits()[10] == 7, "Pit 10 should have 1 more stone after move");
        Assert.isTrue(updatedState.pits()[11] == 6, "Pit 11 should be the same after move");
    }
}

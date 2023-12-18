package mancala;

import lombok.extern.slf4j.Slf4j;
import mancala.game.GameData;
import mancala.game.IGameService;
import mancala.game.logic.setup.MancalaGameMode;
import mancala.game.logic.state.GameState;
import mancala.game.utils.MancalaStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Alexander on 12/11/2023
 */
@SpringBootTest
@Slf4j
public class ClassicMancalaTests {
    @Autowired
    private IGameService gameService;
    private GameData testGameData= new GameData("test_game", List.of("host", "guest"), MancalaGameMode.Classic);

    @Test
    public void testInitialGameState() {
        log.info("---> Starting test: testInitialGameState");
        GameState initialState = gameService.startGame(testGameData);

        Assert.isTrue(initialState.pits().length == 14, "Should have 14 pits including stores");
        Assert.isTrue(initialState.pits()[6] == 0, "Player's store is empty initially");
        Assert.isTrue(initialState.pits()[13] == 0, "Opponent's store is empty initially");
        Assert.isTrue(Arrays.stream(initialState.pits(), 0, 6).allMatch(pit -> pit == 6), "Each player's pit should have 6 stones initially");
        Assert.isTrue(Arrays.stream(initialState.pits(), 7, 13).allMatch(pit -> pit == 6), "Each opponent's pit should have 6 stones initially");
    }

    @Test
    public void testMakeMove() {
        log.info("---> Starting test: testMakeMove");
        GameState gameState = gameService.startGame(testGameData);
        // Make a move and get the updated state
        GameState updatedState = gameService.makeMove(gameState, 3);

        Assert.isTrue(updatedState.pits()[3] == 0, "Pit 3 should be empty after move");
        Assert.isTrue(updatedState.pits()[4] == 7, "Pit 4 should have 1 more stone after move");
        Assert.isTrue(updatedState.pits()[9] == 7, "Pit 10 should have 1 more stone after move");
        Assert.isTrue(updatedState.pits()[10] == 6, "Pit 11 should be the same after move");
    }

    @Test
    public void testExtraTurnRule() {
        log.info("---> Starting test: testExtraTurnRule");
        GameState gameState = gameService.startGame(testGameData);
        int player = gameState.currentPlayer();
        GameState updatedState = gameService.makeMove(gameState, 0);
        Assert.isTrue(updatedState.currentPlayer() == player, "Game should have given another turnNumber to active player");

    }

    @Test
    public void testCaptureRule() {
        log.info("---> Starting test: testCaptureRule");
        GameState gameState = gameService.startGame(testGameData);
        gameState.pits()[0] = 1;
        gameState.pits()[1] = 0;
        GameState updatedState = gameService.makeMove(gameState, 0);

        Assert.isTrue(updatedState.pits()[11] == 0, "Game should have captured stones");
        Assert.isTrue(updatedState.pits()[6] == 6, "Game should have put captured stones into player's store");
    }

    @Test
    public void testGameOver() {
        log.info("---> Starting test: testGameOver");
        GameState gameState = gameService.startGame(testGameData);
        int[] pits = new int[]{
                6, 0, 0, 0, 0, 0, 1, 7, 7, 6, 6, 6, 6, 0
        };
        gameState = GameState.builder().copyFields(gameState).pits(pits).build();

        GameState updatedState = gameService.makeMove(gameState, 0);
        Assert.isTrue(updatedState.gameOver(), "Game should have ended");

    }
    @Test
    public void testGame() {
        log.info("---> Starting test: testGame");
        int[] pits;
        GameState state = gameService.startGame(testGameData);

        state = gameService.makeMove(state, 2);

        pits = new int[]{
                6, 6, 0, 7, 7, 7, 1, 7, 7, 6, 6, 6, 6, 0
        };
        assertEquals(MancalaStringUtils.prettyPits(state.pits()), MancalaStringUtils.prettyPits(pits), "Different pits");

        state = gameService.makeMove(state, 0+7);
        pits = new int[]{
                7, 6, 0, 7, 7, 7, 1, 0, 8, 7, 7, 7, 7, 1
        };
        assertEquals(MancalaStringUtils.prettyPits(state.pits()), MancalaStringUtils.prettyPits(pits), "Different pits");

        state = gameService.makeMove(state, 3);
        pits = new int[]{
                7, 6, 0, 0, 8, 8, 2, 1, 9, 8, 8, 7, 7, 1
        };
        assertEquals(MancalaStringUtils.prettyPits(state.pits()), MancalaStringUtils.prettyPits(pits), "Different pits");

        state = gameService.makeMove(state, 1+7);
        pits = new int[]{
                8, 7, 1, 1, 8, 8, 2, 1, 0, 9, 9, 8, 8, 2
        };
        assertEquals(MancalaStringUtils.prettyPits(state.pits()), MancalaStringUtils.prettyPits(pits), "Different pits");

        state = gameService.makeMove(state, 4);
        pits = new int[]{
                8, 7, 1, 1, 0, 9, 3, 2, 1, 10, 10, 9, 9, 2
        };
        assertEquals(MancalaStringUtils.prettyPits(state.pits()), MancalaStringUtils.prettyPits(pits), "Different pits");
    }

}

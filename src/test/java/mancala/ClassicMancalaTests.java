package mancala;

import lombok.extern.slf4j.Slf4j;
import mancala.common.MancalaGameMode;
import mancala.common.utils.MancalaStringUtils;
import mancala.engine.logic.setup.GameSetupData;
import mancala.engine.logic.state.GameState;
import mancala.web.configuration.MancalaConfig;
import mancala.web.configuration.ServiceConfig;
import mancala.web.game.IGameService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Set;

import static mancala.engine.logic.setup.ClassicMancalaSetup.TOTAL_STONES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 
 */
@SpringBootTest(classes={MancalaConfig.class, ServiceConfig.class})
@Slf4j
public class ClassicMancalaTests {
    @Autowired
    private IGameService gameService;
    private GameSetupData testGameSetupData = new GameSetupData("test_game", Set.of("host", "guest"), MancalaGameMode.Classic);

    @Test
    public void testInitialGameState() {
        log.info("---> Starting test: testInitialGameState");
        GameState initialState = gameService.startGame(testGameSetupData);

        assertTrue(initialState.pits().length == 14, "Should have 14 pits including stores");
        assertTrue(initialState.pits()[6] == 0, "Player's store is empty initially");
        assertTrue(initialState.pits()[13] == 0, "Opponent's store is empty initially");
        assertTrue(Arrays.stream(initialState.pits(), 0, 6).allMatch(pit -> pit == 6), "Each player's pit should have 6 stones initially");
        assertTrue(Arrays.stream(initialState.pits(), 7, 13).allMatch(pit -> pit == 6), "Each opponent's pit should have 6 stones initially");
    }

    @Test
    public void testMakeMove() {
        log.info("---> Starting test: testMakeMove");
        GameState gameState = gameService.startGame(testGameSetupData);
        // Make a move and get the updated state
        GameState updatedState = gameService.makeMove(gameState, 3);
        assertTrue(updatedState.pits()[3] == 0, "Pit 3 should be empty after move");
        assertTrue(updatedState.pits()[4] == 7, "Pit 4 should have 1 more stone after move");
        assertTrue(updatedState.pits()[9] == 7, "Pit 10 should have 1 more stone after move");
        assertTrue(updatedState.pits()[10] == 6, "Pit 11 should be the same after move");
    }

    @Test
    public void testExtraTurnRule() {
        log.info("---> Starting test: testExtraTurnRule");
        GameState state = gameService.startGame(testGameSetupData);
        int player = state.currentPlayer();
        GameState updatedState = gameService.makeMove(state, 0);
        assertTrue(updatedState.currentPlayer() == player, "Game should have given another turnNumber to active player");

        assertEquals(Arrays.stream(state.pits()).sum(), TOTAL_STONES, "Total number of stones changed");
    }

    @Test
    public void testCaptureRule() {
        log.info("---> Starting test: testCaptureRule");
        GameState state = gameService.startGame(testGameSetupData);
        state.pits()[0] = 1;
        state.pits()[1] = 0;
        int total=Arrays.stream(state.pits()).sum();
         state = gameService.makeMove(state, 0);

        assertTrue(state.pits()[11] == 0, "Game should have captured stones");
        assertTrue(state.pits()[6] == 7, "Game should have put captured stones into player's store");
        assertEquals(Arrays.stream(state.pits()).sum(), total, "Total number of stones changed");
    }

    @Test
    @DisplayName("Game Over Test")
    public void testGameOver() {
        log.info("---> Starting test: testGameOver");
        GameState gameState = gameService.startGame(testGameSetupData);
        int[] pits = new int[]{
                0, 0, 0, 0, 0, 1, 1, 7, 7, 6, 6, 6, 6, 0
        };
        gameState = GameState.builder().copyFields(gameState).pits(pits).build();

        GameState updatedState = gameService.makeMove(gameState, 5);
        assertTrue(updatedState.gameOver(), "Game should have ended because player clears their last non-empty pit");

    }

    @Test
    @DisplayName("Game Not Over Test")
    public void testNotGameOver() {
        log.info("---> Starting test: testGameNotOver");
        GameState gameState = gameService.startGame(testGameSetupData);
        int[] pits = new int[]{
                1, 0, 0, 0, 0, 1, 1, 7, 7, 6, 6, 6, 6, 0
        };
        gameState = GameState.builder().copyFields(gameState).pits(pits).build();

        GameState updatedState = gameService.makeMove(gameState, 5);
        assertTrue(!updatedState.gameOver(), "Game should not have ended");

    }
    @Test
    @DisplayName("Game Test")
    public void testGame5Turns() {
        log.info("---> Starting test: testGame");
        int[] pits;
        GameState state = gameService.startGame(testGameSetupData);

        state = gameService.makeMove(state, 2);

        pits = new int[]{
                6, 6, 0, 7, 7, 7, 1, 7, 7, 6, 6, 6, 6, 0
        };
        assertEquals(Arrays.stream(state.pits()).sum(), TOTAL_STONES, "Total number of stones changed");
        assertEquals(MancalaStringUtils.prettyPits(state.pits()), MancalaStringUtils.prettyPits(pits), "Different pits");

        state = gameService.makeMove(state, 0+7);
        pits = new int[]{
                7, 6, 0, 7, 7, 7, 1, 0, 8, 7, 7, 7, 7, 1
        };
        assertEquals(Arrays.stream(state.pits()).sum(), TOTAL_STONES, "Total number of stones changed");
        assertEquals(MancalaStringUtils.prettyPits(state.pits()), MancalaStringUtils.prettyPits(pits), "Different pits");

        state = gameService.makeMove(state, 3);
        pits = new int[]{
                7, 6, 0, 0, 8, 8, 2, 1, 9, 8, 8, 7, 7, 1
        };
        assertEquals(Arrays.stream(state.pits()).sum(), TOTAL_STONES, "Total number of stones changed");
        assertEquals(MancalaStringUtils.prettyPits(state.pits()), MancalaStringUtils.prettyPits(pits), "Different pits");

        state = gameService.makeMove(state, 1+7);
        pits = new int[]{
                8, 7, 1, 1, 8, 8, 2, 1, 0, 9, 9, 8, 8, 2
        };
        assertEquals(Arrays.stream(state.pits()).sum(), TOTAL_STONES, "Total number of stones changed");
        assertEquals(MancalaStringUtils.prettyPits(state.pits()), MancalaStringUtils.prettyPits(pits), "Different pits");

        state = gameService.makeMove(state, 4);
        pits = new int[]{
                8, 7, 1, 1, 0, 9, 3, 2, 1, 10, 10, 9, 9, 2
        };
        assertEquals(Arrays.stream(state.pits()).sum(), TOTAL_STONES, "Total number of stones changed");
        assertEquals(MancalaStringUtils.prettyPits(state.pits()), MancalaStringUtils.prettyPits(pits), "Different pits");

    }

}

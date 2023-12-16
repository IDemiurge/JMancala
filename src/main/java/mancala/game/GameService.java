package mancala.game;

import lombok.extern.slf4j.Slf4j;
import mancala.game.logic.handler.ITipHandler;
import mancala.game.logic.setup.MancalaSetup;
import mancala.game.logic.handler.PitHandler;
import mancala.game.logic.state.TurnState;
import mancala.game.logic.state.GameState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static mancala.game.logic.state.TurnState.StateType.NORMAL;

@Service
@Slf4j
public class GameService implements IGameService {

    @Autowired
    PitHandler pitHandler;
    @Autowired
    ITipHandler tipHandler;
    @Autowired
    MancalaSetup setup;
    //TODO pass as parameter on create()!


    @Override
    public GameState join(GameState state) {
        return join(state, "GUEST");
    }

    @Override
    public GameState join(GameState state, String playerName) {
        log.info("\nGame joined by " + playerName);
        return state.addPlayer(playerName);
    }

    @Override
    public GameState host() {
        return host("HOST");
    }

    @Override
    public GameState host(String playerName) {
        log.info("\nHosting Game as " + playerName);
        return createGameState(playerName);
    }

    private String generateId(String playerName, MancalaSetup setup) {
        return playerName + "-" + setup.getName();
    }

    @Override
    public GameState makeMove(GameState state, int pitIndex) {
        //TODO validate input?
        //TODO getPlayerName(index)
        log.info("\n" + state.currentPlayerName() + " player move: " + pitIndex);
        logState(state);
        TurnState turnState = pitHandler.placeStone(createStartTurnState(state, pitIndex));
        log.info("\nTurn done");
        logState(state);
        return createNewGameState(turnState, state);
    }

    private void logState(GameState state) {
        StringBuilder builder = new StringBuilder();
        builder.append(mancala.game.utils.MancalaStringUtils.prettyPits(state.pits(), setup));
        log.info("\nState: " + builder);
    }

    private TurnState createStartTurnState(GameState state, int pitIndex) {
        int inHand = state.pits()[pitIndex];
        state.pits()[pitIndex] = 0;
        return new TurnState(state.pits(), inHand, state.currentPlayer(), ++pitIndex, NORMAL);
    }

    public GameState createGameState(String playerName) {
        List<String> players = new ArrayList<>();
        players.add(playerName);
        String identifier = generateId(playerName, setup);
        return  GameState.builder()
                .turnNumber(1)
                .pits(setup.startingPits())
                .players(players)
                .statusMessage(tipHandler.getStartingTip(playerName))
                .currentPlayer(0)
                .identifier(identifier)
                .build();
    }

    public GameState createNewGameState(TurnState turnState, GameState state) {
        int currentPlayer = turnState.playerIndex();
        int turn = state.turnNumber();
        GameState.Builder builder = GameState.builder()
                .copyFields(state)
                .pits(turnState.pits())
                .statusMessage(tipHandler.getTip(turnState, state));

        switch (turnState.type()) {
            case PLAYER_DONE:
                if (turnState.playerIndex() == 0) {
                    turn++;  //increment turnNumber only when host moves
                }
                currentPlayer = nextPlayer(turnState.playerIndex());
            case NORMAL:
                return  builder
                        .currentPlayer(currentPlayer)
                        .turnNumber(turn)
                        .build();
            case GAME_OVER:
                return  builder
                        .currentPlayer(currentPlayer)
                        .turnNumber(turn)
                        .victor(state.players().get( turnState.getWinnerIndex()))
                        .build();
                // return new GameState(turn, turnState.pits(), player, tipHandler.getGameOverTip(turnState, state),
                //         state.players(), state.identifier(), true);
        }
        throw new RuntimeException("Unknown turnNumber state type: " + turnState.type());
    }

    private int nextPlayer(int playerIndex) {
        return (playerIndex + 1) % setup.playersNumber();
    }
}

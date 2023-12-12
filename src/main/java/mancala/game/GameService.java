package mancala.game;

import mancala.game.logic.handler.ITipHandler;
import mancala.game.logic.setup.MancalaSetup;
import mancala.game.logic.handler.PitHandler;
import mancala.game.logic.state.turn.ITurnState;
import mancala.game.logic.state.turn.TurnState;
import mancala.game.logic.state.GameState;
import org.springframework.beans.factory.annotation.Autowired;

import static mancala.game.logic.state.turn.TurnState.StateType.NORMAL;

/**
 * Created by Alexander on 12/11/2023
 */
public class GameService implements IGameService {

    @Autowired
    PitHandler pitHandler;
    @Autowired
    MancalaSetup setup;
    @Autowired
    ITipHandler tipHandler;

    GameState state; //previous, must be kept

    public GameState init(int playerIndex) {
        this.setup.setPlayerIndex(playerIndex);
        this.state = createGameState(setup);
        return this.state;
    }

    @Override
    public GameState makeMove(int pitIndex) {
        /*
                create intermediary gamestates to calculate the final one?
         */
        //TODO validate input?

        ITurnState turnState =  pitHandler.placeStone(createStartTurnState(pitIndex));

        return createNewGameState(turnState);
    }

    private TurnState createStartTurnState(int pitIndex) {
        int inHand=state.pits()[pitIndex];
        state.pits()[pitIndex] = 0;
        return new TurnState(state.pits(), inHand, state.currentPlayer(), ++pitIndex, NORMAL);
    }
    public GameState createGameState(MancalaSetup setup) {
        return new GameState(setup.startingPits(), 0, tipHandler.getStartingTip());
    }

    public GameState createNewGameState(ITurnState turnState) {
        int player = turnState.playerIndex();
        switch (turnState.type()) {
            case PLAYER_DONE:
                player = nextPlayer(turnState.playerIndex());
            case NORMAL:
                return new GameState(turnState.pits(), player, tipHandler.getTip(turnState));
            case GAME_OVER:
                //TODO
                break;
        }
        throw new RuntimeException("Unknown turn state type: " + turnState.type());
    }

    private int nextPlayer(int playerIndex) {
        return (playerIndex+1)%setup.playersNumber();
    }
}

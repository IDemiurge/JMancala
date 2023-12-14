package mancala.game;

import mancala.game.logic.handler.ITipHandler;
import mancala.game.logic.setup.MancalaSetup;
import mancala.game.logic.handler.PitHandler;
import mancala.game.logic.state.TurnState;
import mancala.game.logic.state.GameState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static mancala.game.logic.state.TurnState.StateType.NORMAL;

@Service
public class GameService implements IGameService {

    @Autowired
    PitHandler pitHandler;
    @Autowired
    ITipHandler tipHandler;
    @Autowired MancalaSetup setup;

    public void join(GameState state) {
        // map.put()
        //as next player
        //signal that game can be started

    }

    @Override
    public GameState host() {
        return  createGameState();
    }

    @Override
    public GameState makeMove(GameState state, int pitIndex) {
        /*
                create intermediary gamestates to calculate the final one?
         */
        //TODO validate input?

        TurnState turnState =  pitHandler.placeStone(createStartTurnState(state, pitIndex));

        return createNewGameState(turnState);
    }

    private TurnState createStartTurnState(GameState state, int pitIndex) {
        int inHand=state.pits()[pitIndex];
        state.pits()[pitIndex] = 0;
        return new TurnState(state.pits(), inHand, state.currentPlayer(), ++pitIndex, NORMAL);
    }
    public GameState createGameState() {
        return new GameState(setup.startingPits(), 0, tipHandler.getStartingTip());
    }

    public GameState createNewGameState(TurnState turnState) {
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

package mancala.web.controller;

import lombok.extern.slf4j.Slf4j;
import mancala.common.exception.MoveValidationException;
import mancala.engine.logic.setup.IMancalaSetupProvider;
import mancala.engine.logic.setup.MancalaSetup;
import mancala.web.room.Room;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

/**
 * Created by Alexander on 12/19/2023
 */
@Slf4j
public class MoveValidator {

    public static final String WRONG_PLAYER_INDEX = "Invalid player index: ";
    public static final String WRONG_PIT_INDEX = "Invalid pit index: ";
    public static final String NOT_PLAYERS_PIT = " is not pit index of player #: ";

    @Autowired
    private IMancalaSetupProvider provider;

    //TODO
    public void validate(String userName, int pitIndex, Room room) {
        MancalaSetup setup = provider.createSetup(room.getGameMode());
        int playerIndex = new ArrayList<>(room.getPlayers()).indexOf(userName);
        String error = getValidationErrorMessage(setup, playerIndex, pitIndex);
        if (error != null) {
            log.info("Invalid move request: " + userName + " moves pit #" + pitIndex);
            throw new MoveValidationException(error);
        } else {
            log.info("Valid move request: " + userName + " moves pit #" + pitIndex);
        }
    }

    public String getValidationErrorMessage(MancalaSetup setup, int playerIndex, int pitIndex) {
        if (playerIndex < 0) {
            return WRONG_PLAYER_INDEX + playerIndex;
        }
        if (pitIndex > setup.stores()[playerIndex]) {
            return WRONG_PIT_INDEX + pitIndex;
        }
        if (!setup.isPlayersPit(playerIndex, pitIndex)) {
            return pitIndex + NOT_PLAYERS_PIT + playerIndex;
        }
        return null;
    }
}

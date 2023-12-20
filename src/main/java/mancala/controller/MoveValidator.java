package mancala.controller;

import lombok.extern.slf4j.Slf4j;
import mancala.game.exception.MoveValidationException;
import mancala.game.logic.setup.IMancalaSetupProvider;
import mancala.game.logic.setup.MancalaSetup;
import mancala.room.Room;
import org.slf4j.Marker;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

/**
 * Created by Alexander on 12/19/2023
 */
@Slf4j
public class MoveValidator {

    @Autowired
    IMancalaSetupProvider provider;

    //TODO
    public void validate(String userName, int pitIndex, Room room) {
        MancalaSetup setup = provider.createSetup(room.getGameMode());
        int playerIndex = new ArrayList<>(room.getPlayers()).indexOf(userName);
        String error = getValidationError(setup, playerIndex, pitIndex);
        if (error != null) {
            log.info(Marker.ANY_MARKER, "Invalid move request: ",1,1,1);
            throw new MoveValidationException(error);
        } else {
            log.info("Valid move request: ");
        }
    }

    public String getValidationError(MancalaSetup setup, int playerIndex, int pitIndex) {
        if (playerIndex < 0) {
            //localization
            // return StringConsts.VALIDATION_PLAYER_INDEX;
        }
        if (pitIndex > setup.stores()[playerIndex]) {
            // return StringConsts.VALIDATION_PLAYER_INDEX;
        }
        return null;
}
}

package mancala.configuration;

import mancala.game.logic.setup.IMancalaSetupProvider;
import mancala.game.logic.setup.MancalaSetupProvider;
import mancala.room.GameRoom;
import mancala.room.GameRoomSorter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Comparator;

/**
 * Created by Alexander on 12/17/2023
 */
@Configuration
public class MancalaConfig {

    @Bean
    IMancalaSetupProvider setupProvider() {
        return new MancalaSetupProvider();
    }

}

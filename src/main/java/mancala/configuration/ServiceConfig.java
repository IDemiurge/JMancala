package mancala.configuration;

import mancala.room.GameIdGenerator;
import mancala.room.GameRoom;
import mancala.room.GameRoomSorter;
import mancala.utils.SessionTools;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Comparator;

/**
 * Created by Alexander on 12/17/2023
 */
@Configuration
public class ServiceConfig {

    @Bean
    public GameIdGenerator idGenerator(){
        return new GameIdGenerator();
    }

    @Bean
    Comparator<GameRoom> sorter() {
        return new GameRoomSorter();
    }

    @Bean
    public SessionTools sessionTools(){
        return new SessionTools();
    }
}

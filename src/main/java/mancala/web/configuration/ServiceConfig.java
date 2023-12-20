package mancala.web.configuration;

import mancala.web.controller.MoveValidator;
import mancala.web.game.GameService;
import mancala.web.game.IGameService;
import mancala.web.room.GameIdGenerator;
import mancala.web.room.Room;
import mancala.web.room.RoomSorter;
import mancala.web.utils.SessionTools;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Comparator;

/**
 * 
 */
@Configuration
public class ServiceConfig {

    @Bean
    public MoveValidator moveValidator() {
        return new MoveValidator();
    }

    @Bean
    public GameIdGenerator idGenerator() {
        return new GameIdGenerator();
    }

    @Bean
    Comparator<Room> sorter() {
        return new RoomSorter();
    }

    @Bean
    public SessionTools sessionTools() {
        return new SessionTools();
    }

    @Bean
    public IGameService gameService() {
        return new GameService();
    }
}

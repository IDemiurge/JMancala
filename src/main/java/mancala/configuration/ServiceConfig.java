package mancala.configuration;

import mancala.controller.MoveValidator;
import mancala.room.GameIdGenerator;
import mancala.room.Room;
import mancala.room.RoomSorter;
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
}

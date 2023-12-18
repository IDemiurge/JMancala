package mancala.configuration;

import mancala.game.GameService;
import mancala.room.GameRoomService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Alexander on 12/17/2023
 */
@Configuration
public class ServiceConfig {

    @Bean
    public GameService gameService(){
        return new GameService();
    }

    @Bean
    public GameRoomService gameRoomService(){
        return new GameRoomService();
    }
}

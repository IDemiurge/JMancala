package mancala.configuration;

import mancala.game.logic.handler.ITipHandler;
import mancala.game.logic.handler.OutcomeHandler;
import mancala.game.logic.handler.PitHandler;
import mancala.game.logic.handler.TipHandler;
import mancala.room.GameRoom;
import mancala.room.GameRoomSorter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Comparator;

/**
 * Created by Alexander on 12/17/2023
 */
@Configuration
public class BaseMancalaConfig {

    @Bean
    public OutcomeHandler outcomeHandler(){
        return new OutcomeHandler();
    }

    @Bean
    public ITipHandler tipHandler(){
        return new TipHandler();
    }

    @Bean
    public PitHandler pitHandler(){
        return new PitHandler();
    }

    @Bean
    Comparator<GameRoom> sorter(){
        return new GameRoomSorter();
    }
}

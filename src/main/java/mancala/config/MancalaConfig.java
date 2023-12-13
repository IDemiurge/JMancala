package mancala.config;

import mancala.game.GameService;
import mancala.game.logic.handler.OutcomeHandler;
import mancala.game.logic.handler.PitHandler;
import mancala.game.logic.handler.ITipHandler;
import mancala.game.logic.handler.TipHandler;
import mancala.game.logic.handler.rules.IRulesHandler;
import mancala.game.logic.handler.rules.ClassicRulesHandler;
import mancala.game.logic.setup.ClassicMancalaSetup;
import mancala.game.logic.setup.MancalaSetup;
import mancala.session.SessionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Alexander on 12/11/2023
 */
@Configuration
public class MancalaConfig {

    @Bean
    public MancalaSetup getSetup(){
        return new ClassicMancalaSetup();
    }

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
    public GameService gameService(){
        return new GameService();
    }

    @Bean
    public SessionService sessionService(){
        return new SessionService();
    }

    @Bean
    public IRulesHandler rulesHandler(){
        return new ClassicRulesHandler();
    }
}

package mancala.configuration;

import mancala.game.logic.handler.rules.IRulesHandler;
import mancala.game.logic.handler.rules.ClassicRulesHandler;
import mancala.game.logic.setup.ClassicMancalaSetup;
import mancala.game.logic.setup.MancalaSetup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Alexander on 12/11/2023
 */
@Configuration
public class ClassicMancalaConfig {

    @Bean
    public MancalaSetup getSetup(){
        return new ClassicMancalaSetup();
    }

    @Bean
    public IRulesHandler rulesHandler(){
        return new ClassicRulesHandler();
    }


}

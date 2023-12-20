package mancala.web.configuration;

import mancala.engine.logic.setup.IMancalaSetupProvider;
import mancala.engine.logic.setup.MancalaSetupProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 */
@Configuration
public class MancalaConfig {

    @Bean
    IMancalaSetupProvider setupProvider() {
        return new MancalaSetupProvider();
    }

}

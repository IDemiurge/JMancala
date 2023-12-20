package mancala.web.configuration;

import mancala.engine.logic.setup.IMancalaSetupProvider;
import mancala.engine.logic.setup.MancalaSetupProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

package mancala.web.configuration;

import mancala.web.user.UserStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 */
@Configuration
public class SecurityConfig {
    @Bean
    public UserStore userStore(){
        return new UserStore();
    }
}

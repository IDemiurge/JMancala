package mancala.configuration;

import mancala.user.UserStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Alexander on 12/17/2023
 */
@Configuration
public class SecurityConfig {
    @Bean
    public UserStore userStore(){
        return new UserStore();
    }
}

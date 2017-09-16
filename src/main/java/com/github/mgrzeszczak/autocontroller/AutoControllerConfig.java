package com.github.mgrzeszczak.autocontroller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutoControllerConfig {

    @Bean
    public ExposeAwareHandlerMapping exposeAwareHandlerMapping() {
        return new ExposeAwareHandlerMapping();
    }

}

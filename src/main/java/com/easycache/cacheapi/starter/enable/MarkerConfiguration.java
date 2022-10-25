package com.easycache.cacheapi.starter.enable;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : zach
 */
@Configuration
public class MarkerConfiguration {

    @Bean
    public Mark getMark() {
        return new Mark();
    }

    public static class Mark {
        public Mark() {
        }
    }
}

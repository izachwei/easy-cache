package com.easycache.cacheapi.enable;

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

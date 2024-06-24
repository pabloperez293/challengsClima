package com.pablo.challengsClima.WebClientConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient.Builder webClientBuilder(){
// crea y devuelve instancia de wbc.blr
        return WebClient.builder();
    }
}

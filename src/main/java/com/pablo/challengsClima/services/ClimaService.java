package com.pablo.challengsClima.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Service
public class ClimaService {

    private final WebClient webClient;

// Properties
    @Value("${api.key}")
    private String apiKey;


//  const inyecta y crea instancia
    public ClimaService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://api.openweathermap.org/data/2.5").build();
    }

    public Mono<ResponseEntity<String>> obtenerClimaPorId(Integer id) {
        return obtenerClima("id=" + id);
    }

    public Mono<ResponseEntity<String>> obtenerClimaPorNombre(String nombre) {
        return obtenerClima("q=" + nombre);    }

// Metodo para llamar Api
    private Mono<ResponseEntity<String>> obtenerClima(String queryParam) {
       String url = UriComponentsBuilder.fromUriString("/weather")
               .queryParam(queryParam)
               .queryParam("appid",apiKey)
               .build()
               .toUriString();

// Solic http
       return webClient.get()
               .uri(url)
               .retrieve()
               .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.error(new RuntimeException("Error de autentificacion")))
               .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new RuntimeException("Error al obtener datos del clima")))
               .bodyToMono(String.class)
               .map(response -> ResponseEntity.ok(response))
               .onErrorResume(e -> {
// Manejo de errores
                   if (e instanceof HttpClientErrorException.Unauthorized) {
                       return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error de autenticación"));
                   } else if (e instanceof HttpClientErrorException) {
                       return Mono.just(ResponseEntity.status(((HttpClientErrorException) e).getStatusCode()).body("Error al obtener datos del clima: " + e.getMessage()));
                   } else {
                       return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor"));
                   }
               });
    }
}


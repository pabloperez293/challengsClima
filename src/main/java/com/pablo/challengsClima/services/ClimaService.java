package com.pablo.challengsClima.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ClimaService {

    @Value("${api.key}")
    private String apiKey;

    public ResponseEntity<String> obtenerClimaPorId(Integer id) {
        return obtenerClima("id=" + id);
    }

    public ResponseEntity<String> obtenerClimaPorNombre(String nombre) {
        return obtenerClima("q=" + nombre);    }

    private ResponseEntity<String> obtenerClima(String queryParam) {
        try {
            String url = "http://api.openweathermap.org/data/2.5/weather?" + queryParam + "&appid=" + apiKey;

            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);

            return ResponseEntity.ok(response);
        } catch (HttpClientErrorException.Unauthorized e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error de autenticación");
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body("Error al obtener datos del clima: " + e.getMessage());
        }
    }
}


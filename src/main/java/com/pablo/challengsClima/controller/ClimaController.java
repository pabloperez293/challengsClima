package com.pablo.challengsClima.controller;

import com.pablo.challengsClima.services.ClimaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClimaController {

    @Autowired
    private ClimaService climaService;

    @GetMapping("/clima/id/{id}")
    public ResponseEntity<String> obtenerClimaPorId(@PathVariable Integer id) {
        return climaService.obtenerClimaPorId(id);
    }

    @GetMapping("/clima/nombre/{nombre}")
    public ResponseEntity<String> obtenerClimaPorNombre(@PathVariable String nombre) {
        return climaService.obtenerClimaPorNombre(nombre);
    }
}

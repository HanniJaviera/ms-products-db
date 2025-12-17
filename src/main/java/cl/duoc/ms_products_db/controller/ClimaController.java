package cl.duoc.ms_products_db.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import cl.duoc.ms_products_db.service.ClimaService;



@RestController
@RequestMapping("/clima")
public class ClimaController {

    @Autowired
    private ClimaService climaService;

    @GetMapping
    public ResponseEntity<?> obtenerClima(@RequestParam String ciudad) {
        try {
            Double temp = climaService.obtenerTemperaturaActual(ciudad);
            return ResponseEntity.ok(temp);
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Servicio de clima no disponible");
        }
    }
}


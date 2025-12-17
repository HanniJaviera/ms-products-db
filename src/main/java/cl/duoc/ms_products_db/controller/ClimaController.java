package cl.duoc.ms_products_db.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import cl.duoc.ms_products_db.service.ClimaService;



@RestController
@RequestMapping("/clima")
@CrossOrigin(origins = "*")
public class ClimaController {

    @Autowired
    private ClimaService climaService;

    @GetMapping
    public ResponseEntity<?> clima(@RequestParam String ciudad) {
        try {
            return ResponseEntity.ok(climaService.obtenerClima(ciudad));
        } catch (Exception e) {
            e.printStackTrace(); // IMPORTANTE para logs
            return ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("Servicio de clima no disponible");
        }
    }
}

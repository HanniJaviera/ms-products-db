package cl.duoc.ms_products_db.controller;

import cl.duoc.ms_products_db.service.ClimaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/clima")
@CrossOrigin(origins = "*") // Permite peticiones desde tu Frontend React
public class ClimaController {

    @Autowired
    private ClimaService climaService;

    @GetMapping
    public ResponseEntity<?> obtenerClima(@RequestParam String ciudad) {
        if (ciudad == null || ciudad.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Debe proporcionar una ciudad"));
        }

        Map<String, Object> clima = climaService.obtenerClima(ciudad);

        if (clima != null) {
            return ResponseEntity.ok(clima);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
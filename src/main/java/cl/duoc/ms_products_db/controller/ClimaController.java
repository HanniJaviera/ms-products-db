package cl.duoc.ms_products_db.controller;

import cl.duoc.ms_products_db.service.ClimaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/clima")
// Permitimos que React (localhost o Vercel) pueda consumir esta API sin bloqueos
@CrossOrigin(origins = "*") 
public class ClimaController {

    @Autowired
    private ClimaService climaService;

    // Endpoint: GET /clima?ciudad=NombreCiudad
    @GetMapping
    public ResponseEntity<?> obtenerClima(@RequestParam(required = false) String ciudad) {
        
        // 1. Validación básica de entrada
        if (ciudad == null || ciudad.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Debe proporcionar una ciudad. Ejemplo: /clima?ciudad=Santiago"));
        }

        try {
            Map<String, Object> clima = climaService.obtenerClima(ciudad);

            if (clima != null) {
                return ResponseEntity.ok(clima);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", "Error interno al procesar el clima"));
        }
    }
}
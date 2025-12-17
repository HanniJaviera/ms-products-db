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
            // 2. Llamamos al servicio para obtener los datos
            Map<String, Object> clima = climaService.obtenerClima(ciudad);

            if (clima != null) {
                // 3. Éxito: Devolvemos los datos (HTTP 200)
                return ResponseEntity.ok(clima);
            } else {
                // 4. Fallo: No se encontró la ciudad o hubo error en la API externa (HTTP 404)
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // 5. Error interno no controlado (HTTP 500)
            // IMPRIMIMOS EL ERROR EN CONSOLA PARA DEPURAR
            System.err.println("--- ERROR EN CLIMACONTROLLER ---");
            e.printStackTrace();
            
            // Devolvemos el detalle del error para saber el problema
            return ResponseEntity.internalServerError().body(Map.of(
                "error", "Error interno al procesar el clima",
                "mensaje", e.getMessage() != null ? e.getMessage() : "Excepción desconocida"
            ));
        }
    }
}
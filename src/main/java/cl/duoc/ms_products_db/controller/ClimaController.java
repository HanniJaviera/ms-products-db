package cl.duoc.ms_products_db.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cl.duoc.ms_products_db.service.ClimaService;
import java.util.Map;

@RestController
@RequestMapping("/clima")
public class ClimaController {

    @Autowired
    private ClimaService climaService;

    @GetMapping
    public ResponseEntity<?> obtenerClimaPorCiudad(@RequestParam String ciudad) {
        if (ciudad == null || ciudad.isEmpty()) {
            return new ResponseEntity<>("La ciudad es requerida", HttpStatus.BAD_REQUEST);
        }

        Map<String, Object> respuestaClima = climaService.obtenerClima(ciudad);

        if (respuestaClima != null) {
            return new ResponseEntity<>(respuestaClima, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se pudo obtener el clima para esa ciudad", HttpStatus.NOT_FOUND);
        }
    }
}
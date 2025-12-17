package cl.duoc.ms_products_db.controller;

import cl.duoc.ms_products_db.service.ClimaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/clima")
@CrossOrigin(origins = "*")
public class ClimaController {

    @Autowired
    private ClimaService climaService;

    @GetMapping
    public String clima(@RequestParam String ciudad) {
        return climaService.obtenerClima(ciudad);
    }
}
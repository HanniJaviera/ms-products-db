package cl.duoc.ms_products_db.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cl.duoc.ms_products_db.model.entities.Venta;
import cl.duoc.ms_products_db.service.VentaService;

@RestController
@RequestMapping("/ventas")
// @CrossOrigin NO es necesario si ya tienes el WebConfig global
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @PostMapping
    public ResponseEntity<Venta> crearVenta(@RequestBody Venta venta) {
        Venta nuevaVenta = ventaService.guardarVenta(venta);
        return new ResponseEntity<>(nuevaVenta, HttpStatus.CREATED);
    }
}

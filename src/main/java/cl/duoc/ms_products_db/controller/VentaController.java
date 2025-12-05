package cl.duoc.ms_products_db.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cl.duoc.ms_products_db.model.entities.Venta;
import cl.duoc.ms_products_db.service.VentaService;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @PostMapping
    public ResponseEntity<Venta> crearVenta(@RequestBody Venta venta) {
        Venta nuevaVenta = ventaService.guardarVenta(venta);
        return new ResponseEntity<>(nuevaVenta, HttpStatus.CREATED);
    }
        @GetMapping
    public ResponseEntity<List<Venta>> listarVentas() {
    
        // Asumiendo que tu servicio tiene un método listarTodas() o findAll()
        List<Venta> ventas = ventaService.listarTodas(); 
        
        if (ventas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(ventas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
        public ResponseEntity<Venta> obtenerVentaPorId(@PathVariable Long id) {
        // Cambiado para recibir el objeto Venta directamente en lugar de Optional
         Venta venta = ventaService.buscarPorId(id);

           if (venta != null) {
            return new ResponseEntity<>(venta, HttpStatus.OK);
            } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
           }
    }
}

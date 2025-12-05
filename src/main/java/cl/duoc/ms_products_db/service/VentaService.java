package cl.duoc.ms_products_db.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cl.duoc.ms_products_db.model.entities.Venta;
import cl.duoc.ms_products_db.model.repositories.VentaRepository;

@Service
public class VentaService {
    
    @Autowired
    private VentaRepository ventaRepository;

    public Venta guardarVenta(Venta venta) {
        return ventaRepository.save(venta);
    }
}

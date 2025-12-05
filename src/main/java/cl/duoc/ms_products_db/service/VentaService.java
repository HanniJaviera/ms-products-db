package cl.duoc.ms_products_db.service;

import java.util.List;

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
    public List<Venta> listarTodas() {
        return ventaRepository.findAll();
}

    public Venta buscarPorId(Long id) {
        return ventaRepository.findById(id).orElse(null);
    }
}

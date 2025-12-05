package cl.duoc.ms_products_db.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cl.duoc.ms_products_db.model.entities.Venta;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
}

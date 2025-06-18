package cl.duoc.ms_products_db.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.ms_products_db.model.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}

package cl.duoc.ms_products_db.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.ms_products_db.model.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByCorreoAndPassword(String correo, String password);
    Usuario findByCorreo(String correo);
}


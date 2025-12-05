package cl.duoc.ms_products_db.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.ms_products_db.model.entities.Usuario;
import cl.duoc.ms_products_db.model.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    // Obtiene 
    public List<Usuario> selectAllUsuario(){
        List<Usuario> listaUsuarios = usuarioRepository.findAll();
        return listaUsuarios;
    }

    // Busca
    public Usuario findUsuarioById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    // Crea
    public Usuario createUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Actualiza
    public Usuario updateUsuario(Long id, Usuario usuarioActualizado) {
        if (usuarioRepository.existsById(id)) {
            usuarioActualizado.setIdUsuario(id); // Se asegura que se actualice el correcto
            return usuarioRepository.save(usuarioActualizado);
        } else {
            return null;
        }
    }

    // Elimina 
    public boolean deleteUsuario(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Usuario validarLogin(String correo, String password) {
    return usuarioRepository.findByCorreoAndPassword(correo, password);
}

public Usuario buscarPorCorreo(String correo) {
    // Asumiendo que tu repositorio tiene findByCorreo
    return usuarioRepository.findByCorreo(correo); 
}
}
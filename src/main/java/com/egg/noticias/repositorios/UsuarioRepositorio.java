
package com.egg.noticias.repositorios;

import com.egg.noticias.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario,String> {
    
    
     Usuario findByNombre( String nombre);
}

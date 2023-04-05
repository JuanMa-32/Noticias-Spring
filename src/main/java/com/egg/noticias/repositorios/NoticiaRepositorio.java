
package com.egg.noticias.repositorios;

import com.egg.noticias.entidades.Noticia;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
//la clase @Repository extiende de JpaRepository que contiene los metodos basicos CRUD (crear,leer,actualizar,eliminar)
public interface NoticiaRepositorio extends JpaRepository<Noticia,String>{
    
    @Query("SELECT n FROM Noticia n WHERE n.titulo LIKE %:q%")
    List<Noticia> buscarPorTitulo(@Param("q") String q);
    
    /*
    public List<Noticia> findByTituloContainingIgnoreCase(String term);
    OTRA FORMA DE HACER UNA BARRA DE BUSQUEDA SIN NECESIDAD DE UNA QUERY
*/
    
}

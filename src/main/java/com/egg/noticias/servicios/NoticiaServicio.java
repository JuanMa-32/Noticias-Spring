
package com.egg.noticias.servicios;

import com.egg.noticias.entidades.Noticia;
import com.egg.noticias.repositorios.NoticiaRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class NoticiaServicio {
    
    @Autowired
    private NoticiaRepositorio noticiaRepo;
    
    @Transactional//simpre que vya a acorruir una modificar en la base de datos debemos usar la anotacion @Transactional
    public void crear(String file,String titulo,String cuerpo) throws Exception{
        //llamo al metodo validar para no crear un objeto noticia con datos null
        Validar(titulo, cuerpo);
        
        Noticia noti = new Noticia();
        noti.setTitulo(titulo);
        noti.setCuerpo(cuerpo);
        noti.setFoto(file);
        
        noticiaRepo.save(noti);        
    }
    
    //metodo para traer todas las noticias de la base de datos
    public List<Noticia> listar(){
        List<Noticia> noticias = new ArrayList();
        noticias= noticiaRepo.findAll();
        
        return noticias;
    }
    
    //Metodo para modificar una noticia
    @Transactional
    public void modificar(String titulo,String cuerpo,String id,String foto) throws Exception{
        Validar(titulo, cuerpo);
        Optional<Noticia> not  = noticiaRepo.findById(id);//uso un OPtional porque puede que no exista esta noticia en la base de datos
        
        if (not != null) {
            Noticia noticia = not.get();
            noticia.setCuerpo(cuerpo);
            noticia.setTitulo(titulo);
            noticia.setFoto(foto);
            
            noticiaRepo.save(noticia);//guardo la noticia en la base de datos con los nuevos valores 
        }
      
    }
    
    @Transactional
    public void delete(String id){
       
        Optional<Noticia> respuesta = noticiaRepo.findById(id);
        if(respuesta!=null){
            Noticia not = respuesta.get();
            noticiaRepo.delete(not);
        }
        
    }
    
    //metodo para validar que no l,legen datos vacios o null
    public void Validar(String titulo,String cuerpo) throws Exception{
        
        if(titulo.isEmpty() || titulo==null){
            throw new Exception("Titulo vacio");
        }
         if(cuerpo.isEmpty() || cuerpo==null){
            throw new Exception("cuerpo de la noticia vacio");
        }
    }
    
    public Noticia findById(String id){
        
        Optional<Noticia>respuesta= noticiaRepo.findById(id);
        if(respuesta.isPresent()){
            Noticia n =respuesta.get();
            return n;
        }
        return null;
    }
    
    public List<Noticia> barraBusqueda(String q) {
        
        List<Noticia> noticiaBarra = noticiaRepo.buscarPorTitulo(q);
        return noticiaBarra;
    }
}

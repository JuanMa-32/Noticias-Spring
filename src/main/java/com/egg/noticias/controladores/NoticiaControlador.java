
package com.egg.noticias.controladores;

import com.egg.noticias.entidades.Noticia;
import com.egg.noticias.servicios.NoticiaServicio;
import com.egg.noticias.servicios.UploadFileServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/noticia")
public class NoticiaControlador {
    
    @Autowired
    private NoticiaServicio noticiaServ;
    
    @Autowired
    private UploadFileServicio upload;
    
    
    @GetMapping("/crear")
    public String crear(){
        return "crear_noti.html";
    }
    
    @PostMapping("/creada")
    public String creada(@RequestParam String titulo,@RequestParam String cuerpo,ModelMap model,MultipartFile file){
        
       
        try {
            String img = upload.saveImagen(file);
            noticiaServ.crear(img,titulo, cuerpo);
            List<Noticia> noticias = noticiaServ.listar();
            model.put("noticias", noticias);
            return "index.html";
        } catch (Exception e) {
            return "crear_noti.html";
        }
    }
    
    @GetMapping("/editar/{id}")
    public String editar(ModelMap model,@PathVariable String id){
        
        Noticia noticia = noticiaServ.findById(id);
        
        model.put("noticia", noticia);
        
        return "edit_noti.html";
    }
    
    
    @PostMapping("/editado")
    public String editar( String titulo, String cuerpo, String id,ModelMap model,MultipartFile foto){
        
       
        try {
            String img = upload.saveImagen(foto);
             noticiaServ.modificar(titulo, cuerpo, id,img);
             return "redirect:/";
        } catch (Exception e) {
             Noticia noticia = noticiaServ.findById(id);
            model.put("noticia", noticia);
            
             return "edit_noti.html";
        }
    }
    
    @GetMapping("/buscarPorTitulo")
    public String buscarPorTitulo(@RequestParam String q,ModelMap model){
         List<Noticia> noticias = noticiaServ.barraBusqueda(q);
            model.put("noticias", noticias);
            
            return "buscar_noti.html";
    }
}


package com.egg.noticias.controladores;

import com.egg.noticias.entidades.Noticia;
import com.egg.noticias.entidades.Usuario;
import com.egg.noticias.servicios.NoticiaServicio;
import com.egg.noticias.servicios.UsuarioServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")//esta anotacion sirve para mappear solicitudes HTTP 
public class InicioControlador {
    @Autowired
    private NoticiaServicio noticiaServ;
    
    @Autowired
    private UsuarioServicio usuarioServ;
    
    @GetMapping("/")
    public String inicio(ModelMap model,HttpSession session){//ModelMap se utiliza para enviar informacion al front
        
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        model.put("usuario",usuario);
         
        List<Noticia> noticias=noticiaServ.listar();
        model.put("noticias", noticias);
        return "index.html";
    }
    
    @GetMapping("/ver/{id}")
    public String verNoticia(@PathVariable String id,ModelMap model){//una @PathVariable sirve para extraer una variable de la URL 
       Noticia noticia= noticiaServ.findById(id);
        
       model.put("noticia", noticia);
       return "noticia.html";
    }
    
    @GetMapping("/registrar")
    public String registrar(){
        return "registrar.html";
    }
    
    @PostMapping("/registrado")
    public String registrado(@RequestParam String nombre,@RequestParam String password,String password2){
        try {
            usuarioServ.crear(nombre, password, password2);
            return"redirect:/";
        } catch (Exception e) {
            return "registrar.html";
        }
    }  
    
    @GetMapping("/login")
    public String login(){
        return "login.html";
    }
}

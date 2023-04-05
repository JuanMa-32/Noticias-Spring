
package com.egg.noticias.controladores;


import com.egg.noticias.servicios.NoticiaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminControlador {
    
    @Autowired
    private NoticiaServicio noticiaServ;
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id){
        noticiaServ.delete(id);
        return "redirect:/";
    }
    
}

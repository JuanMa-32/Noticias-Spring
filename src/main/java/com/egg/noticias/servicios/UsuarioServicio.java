
package com.egg.noticias.servicios;

import com.egg.noticias.entidades.Usuario;
import com.egg.noticias.enumeradores.Roles;
import com.egg.noticias.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepo;
    
    @Override
    public UserDetails loadUserByUsername(String nombre) throws UsernameNotFoundException {
        Usuario us= usuarioRepo.findByNombre(nombre);
        
        if(us!=null){
          
            List<GrantedAuthority> permisos = new ArrayList();//GrantedAuthority es una interfaz en Spring Security que representa una autoridad o permiso que se le ha otorgado a un usuario.
            
            //este código crea una autoridad de seguridad a partir del rol de un usuario,
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+us.getRol().toString());//SimpleGrantedAuthority es una implementación de la interfaz GrantedAuthority de Spring Security. Representa una autoridad que se le ha otorgado a un usuario
            permisos.add(p);//agrego la autoridad de seguridad a la lista de los permisos
            
            
            /*
            Este código utiliza la clase RequestContextHolder de Spring Framework para obtener los atributos de una solicitud web.
            */
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();//La clase RequestContextHolder proporciona un mecanismo para acceder a los atributos de una solicitud web desde cualquier parte del código en una aplicación web.
            
            /*La clase HttpSession representa una sesión HTTP entre un cliente y un servidor web,
            y se utiliza para almacenar información del lado del servidor que debe persistir durante la sesión del usuario en la aplicación
            */
            HttpSession session = attr.getRequest().getSession(true);
            
            //El método setAttribute() de HttpSession se utiliza para almacenar un atributo en la sesión HTTP actual.
            session.setAttribute("usuariosession", us);
            
            //Este código crea un objeto User de Spring Security 
            return new User(us.getNombre(),us.getPassword(),permisos);
        }else{
            return null;
        }
    }
    
    public void crear(String nombre,String password,String password2) throws Exception{
        validar(nombre, password, password2);
        Usuario us = new Usuario();
        us.setNombre(nombre);
        us.setPassword(new BCryptPasswordEncoder().encode(password));
        Date fecha = new Date();
        us.setFechaDeAlta(fecha);
        us.setRol(Roles.USER);
        usuarioRepo.save(us);
    }
    
    public void validar(String nombre,String password,String password2) throws Exception{
        if(nombre.isEmpty() || nombre==null){
            throw new Exception("nombre vacio");
        }
         if(password.isEmpty() || password==null){
            throw new Exception("contraseña vacio");
        }
         if(!password.equals(password2)){
             throw new Exception("las contraseñas no son iguales");
         }
    }
}

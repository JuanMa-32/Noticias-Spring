
package com.egg.noticias;

import com.egg.noticias.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)// para habilitar la seguridad a nivel de método en una aplicación.
public class SeguridadWeb extends WebSecurityConfigurerAdapter{
    
    @Autowired
    public UsuarioServicio usuarioServ;
    
 
    
    /*
    El método "userDetailsService" de "auth" se utiliza para definir un proveedor
    de autenticación que utiliza el servicio de usuario para cargar 
    los detalles de un usuario (como el correo electrónico y la contraseña) desde una fuente de datos.*/
    
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(usuarioServ)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/admin/*").hasRole("ADMIN")
                    .antMatchers("/noticia/*").hasAnyRole("ADMIN","PERIODISTA")
                    .antMatchers("/css/*", "/js/*", "/img/*", "/**")
                    .permitAll()
                .and().formLogin()
                    .loginPage("/login")// especifica la ruta de la página de inicio de sesión.
                    .loginProcessingUrl("/logincheck")//specifica la ruta a la que se enviarán las credenciales de inicio de sesión para su procesamiento y autenticación.
                    .usernameParameter("nombre")//especifican los nombres de los parámetros que se utilizarán para enviar el nombre de usuario y la contraseña al servidor respectivamente.
                    .passwordParameter("password")
                    .defaultSuccessUrl("/")//specifica la página a la que se redirigirá al usuario después de que el inicio de sesión sea exitoso.
                    .permitAll()//ndica que cualquier usuario puede acceder a la página de inicio de sesión sin necesidad de autenticación previa.
                .and().logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login")
                    .permitAll()
                .and().csrf()
                    .disable();
                  
                
    }
    
@Bean
public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
    
    
}

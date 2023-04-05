
package com.egg.noticias.entidades;

import com.egg.noticias.enumeradores.Roles;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
public class Usuario {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String nombre;
    private String password;
    
    @Temporal(TemporalType.DATE)//Esta anotacion siempre se debe poner cuando usamos un dato tipo Date
    private Date fechaDeAlta;

    @Enumerated(EnumType.STRING)//Esta anotacion siempre se debe usar cuando se pone un Enum
    private Roles rol;
    
    
     
}


package com.egg.noticias.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data//Esta anotacion de lombok sirve para crear los metodo setter getter y constructores;
public class Noticia {
    
    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid",strategy="uuid2")
    private String id;
    
    private String titulo;
    private String cuerpo;
    private String foto;
    
   
}

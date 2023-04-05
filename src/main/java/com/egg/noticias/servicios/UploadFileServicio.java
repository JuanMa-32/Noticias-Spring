
package com.egg.noticias.servicios;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileServicio {
    
    private String folder="imagen//";
    
    public String saveImagen(MultipartFile file) throws IOException{
        
        if(!file.isEmpty()){
            byte [] bytes = file.getBytes();
            Path path = Paths.get(folder+file.getOriginalFilename());
            Files.write(path, bytes);
            return file.getOriginalFilename();
        }
        
        return"default.jpg";
    }
}

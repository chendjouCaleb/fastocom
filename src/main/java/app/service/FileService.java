package app.service;

import app.exception.FileTypeException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileService {
    public void checkIfFileIs(MultipartFile file, String type){
        if (!file.getContentType().startsWith(type)){
            throw new FileTypeException("Le format de fichier n'est pas celui requis. Il doit etre : '" + type + '"');
        }
    }

    public void saveUploadFile(MultipartFile file, String uri) throws IOException {

            byte[] bytes = file.getBytes();
            Path path = Paths.get(uri);
            Files.write(path, bytes);
    }
}

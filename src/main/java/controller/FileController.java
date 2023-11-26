package controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@PreAuthorize("hasRole('Supervisor')")
public class FileController {

    @PostMapping("filespdf")
    public String uploadFilePdf(@RequestParam("file") MultipartFile file) throws IOException{
        String originalFilename = file.getOriginalFilename();
        String contentType = file.getContentType();
        if (!contentType.startsWith("application/pdf")) {
            throw new RuntimeException("La extensión del archivo no es PDF");
        }
        Path targetPath = Paths.get("./Documentos", originalFilename);
        file.transferTo(targetPath);
        return originalFilename;
    }
    @PostMapping("/filesdoc")
    public String uploadFileDoc(@RequestParam("file") MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        long size = file.getSize();

        if (!originalFilename.endsWith(".doc")) {
            throw new RuntimeException("La extensión del archivo no es DOC");
        }

        if (size > 2097152) {
            throw new RuntimeException("El tamaño del archivo es superior a 2MB");
        }


        Path targetPath = Paths.get("./Documentos", originalFilename);
        file.transferTo(targetPath);

        return originalFilename;
    }
}

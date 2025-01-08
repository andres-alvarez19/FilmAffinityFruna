package ufro.dci.filmaffinityfruna.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/upload")
public class UploadController {

    @PostMapping("/movie/cover")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file, @RequestParam("movieName") String movieName) {
        return uploadFile(file, movieName, "uploads/images/movies", "");
    }
    @PostMapping("/movie/overview")
    public ResponseEntity<String> uploadOverviewImage(@RequestParam("file") MultipartFile file, @RequestParam("movieName") String movieName) {
        return uploadFile(file, movieName, "uploads/images/movies/overview", "overview");
    }

    @PostMapping("/actor/photo")
    public ResponseEntity<String> uploadActorPhoto(@RequestParam("file") MultipartFile file, @RequestParam("actorName") String actorName) {
        return uploadFile(file, actorName, "uploads/images/actors", "");
    }

    @PostMapping("/director/photo")
    public ResponseEntity<String> uploadDirectorPhoto(@RequestParam("file") MultipartFile file, @RequestParam("directorName") String directorName) {
        return uploadFile(file, directorName, "uploads/images/directors", "");
    }

    private ResponseEntity<String> uploadFile(MultipartFile file, String name, String uploadDirectory, String suffix) {
        if (file.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("No se ha seleccionado ningún archivo.");
        }

        try {
            Path uploadPath = Paths.get(uploadDirectory);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileExtension = getFileExtension(file.getOriginalFilename());
            if (!fileExtension.equals("jpg") && !fileExtension.equals("png")) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Solo se permiten archivos JPG o PNG.");
            }

            String fileName = name.toLowerCase().replace(" ", "-") + (suffix.isEmpty() ? "" : "-" + suffix) + "." + fileExtension;
            Path destination = uploadPath.resolve(fileName);

            Files.copy(
                    file.getInputStream(),
                    destination,
                    StandardCopyOption.REPLACE_EXISTING
            );

            String fileUrl = uploadDirectory + "/" + fileName;

            return ResponseEntity
                    .ok(fileUrl);

        } catch (IOException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al subir el archivo: " + e.getMessage());
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1).toLowerCase();
    }
}
package ufro.dci.filmaffinityfruna.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufro.dci.filmaffinityfruna.model.entity.GenreEntity;
import ufro.dci.filmaffinityfruna.service.GenreService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/genre")
public class GenreRestController {

    private final GenreService genreService;

    @GetMapping()
    public ResponseEntity<GenreEntity> searchByName(@RequestParam(name = "name") String name) {
        try {
            GenreEntity genre = genreService.searchByName(name);
            return new ResponseEntity<>(genre, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public ResponseEntity<String> register(@RequestBody GenreEntity genreEntity) {
        try {
            genreService.register(genreEntity);
            return new ResponseEntity<>("Género registrado correctamente", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Ocurrió un error " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{name}")
    public ResponseEntity<String> update(@PathVariable(name = "name") String name, @RequestBody GenreEntity updatedGenre) {
        try {
            genreService.update(name, updatedGenre);
            return new ResponseEntity<>("Género actualizado correctamente", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Ocurrió un error " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<String> deleteGenreByName(@PathVariable(name = "name") String name) {
        try {
            genreService.deleteGenreByName(name);
            return new ResponseEntity<>("Género eliminado correctamente", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Ocurrió un error " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
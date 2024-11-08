package ufro.dci.filmaffinityfruna.controller;

import jakarta.validation.Valid;
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

    @GetMapping("/search")
    public ResponseEntity<GenreEntity> searchByName(@RequestParam(name = "name") String name) {
        GenreEntity genre = genreService.searchByName(name);
        return new ResponseEntity<>(genre, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid GenreEntity genreEntity) {
        genreService.register(genreEntity);
        return new ResponseEntity<>("Género registrado correctamente", HttpStatus.OK);
    }

    @PutMapping("/update/{name}")
    public ResponseEntity<String> update(@PathVariable(name = "name") String name, @RequestBody GenreEntity updatedGenre) {
        genreService.update(name, updatedGenre);
        return new ResponseEntity<>("Género actualizado correctamente", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<String> deleteGenreByName(@PathVariable(name = "name") String name) {
        genreService.deleteGenreByName(name);
        return new ResponseEntity<>("Género eliminado correctamente", HttpStatus.OK);
    }
}
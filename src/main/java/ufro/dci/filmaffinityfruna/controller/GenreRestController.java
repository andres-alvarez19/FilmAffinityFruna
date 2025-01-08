package ufro.dci.filmaffinityfruna.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ufro.dci.filmaffinityfruna.model.dto.GenreDTO;
import ufro.dci.filmaffinityfruna.service.GenreService;
import ufro.dci.filmaffinityfruna.utils.MessageConstant;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/genre")
public class GenreRestController {

    private final GenreService genreService;

    @GetMapping("/search")
    public ResponseEntity<GenreDTO> searchByName(@RequestParam(name = "name") String name) {
        GenreDTO genre = genreService.searchByName(name);
        return new ResponseEntity<>(genre, HttpStatus.OK);
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> register(@RequestBody @Valid GenreDTO genreDTO) {
        genreService.register(genreDTO.toEntity());
        return new ResponseEntity<>(MessageConstant.REGISTERED, HttpStatus.OK);
    }

    @PutMapping("/update/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> update(@PathVariable(name = "name") String name, @RequestBody GenreDTO updatedGenre) {
        genreService.update(name, updatedGenre.toEntity());
        return new ResponseEntity<>(MessageConstant.UPDATED, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteGenreByName(@PathVariable(name = "name") String name) {
        genreService.deleteGenreByName(name);
        return new ResponseEntity<>(MessageConstant.DELETED, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<GenreDTO>> getAllGenres() {
        List<GenreDTO> genres = genreService.getAllGenres();
        return new ResponseEntity<>(genres, HttpStatus.OK);
    }
}
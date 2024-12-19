package ufro.dci.filmaffinityfruna.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufro.dci.filmaffinityfruna.model.dto.DirectorDTO;
import ufro.dci.filmaffinityfruna.model.dto.MovieDTO;
import ufro.dci.filmaffinityfruna.model.entity.DirectorEntity;
import ufro.dci.filmaffinityfruna.service.DirectorService;
import ufro.dci.filmaffinityfruna.utils.MessageConstant;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/director")
public class DirectorRestController {

    private final DirectorService directorService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid DirectorEntity directorEntity) {
        directorService.register(directorEntity);
        return new ResponseEntity<>(MessageConstant.REGISTERED, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable(name = "id") Long id, @RequestBody DirectorEntity updatedDirector) {
        directorService.update(id, updatedDirector);
        return new ResponseEntity<>(MessageConstant.UPDATED, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDirectorById(@PathVariable(name = "id") Long id) {
        directorService.deleteDirectorById(id);
        return new ResponseEntity<>(MessageConstant.DELETED, HttpStatus.OK);
    }

    @GetMapping("/search/name/{name}")
    public ResponseEntity<List<DirectorDTO>> searchByName(@PathVariable(name = "name") String name) {
        List<DirectorDTO> director = directorService.searchByNameIgnoreCase(name);
        return new ResponseEntity<>(director, HttpStatus.OK);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<DirectorDTO> searchById(@PathVariable(name = "id") Long id) {
        DirectorDTO director = directorService.searchById(id);
        return new ResponseEntity<>(director, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<DirectorDTO>> getAllDirectors() {
        return new ResponseEntity<>(directorService.getAllDirectors(), HttpStatus.OK);
    }

    @GetMapping("/{id}/movies")
    public ResponseEntity<List<MovieDTO>> getMoviesByDirectorId(@PathVariable Long id) {
        List<MovieDTO> movies = directorService.getMoviesByDirectorId(id);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

}
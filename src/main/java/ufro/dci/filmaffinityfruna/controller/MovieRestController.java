package ufro.dci.filmaffinityfruna.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufro.dci.filmaffinityfruna.model.entity.MovieEntity;
import ufro.dci.filmaffinityfruna.service.MovieService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/movie")
public class MovieRestController {

    private final MovieService movieService;

    @GetMapping("/search")
    public ResponseEntity<List<MovieEntity>> searchMoviesByName(@RequestParam(name = "name") String name) {
        try {
            List<MovieEntity> movies = movieService.searchByName(name);
            return new ResponseEntity<>(movies, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody MovieEntity movieEntity) {
        try {
            movieService.register(movieEntity);
            return new ResponseEntity<>("Película registrada correctamente", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Ocurrió un error " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable(name = "id") Long id, @RequestBody MovieEntity updatedMovie) {
        try {
            movieService.update(id, updatedMovie);
            return new ResponseEntity<>("Película actualizada correctamente", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Ocurrió un error " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMovieById(@PathVariable(name = "id") Long id) {
        try {
            movieService.deleteMovieById(id);
            return new ResponseEntity<>("Película eliminada correctamente", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Ocurrió un error " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

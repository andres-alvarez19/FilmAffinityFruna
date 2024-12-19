package ufro.dci.filmaffinityfruna.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufro.dci.filmaffinityfruna.model.dto.MovieDTO;
import ufro.dci.filmaffinityfruna.model.entity.MovieEntity;
import ufro.dci.filmaffinityfruna.service.MovieService;
import ufro.dci.filmaffinityfruna.utils.MessageConstant;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/movie")
public class MovieRestController {

    private final MovieService movieService;

    @GetMapping("/find/{id}")
    public ResponseEntity<MovieDTO> findMovieById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(movieService.findMovieById(id), HttpStatus.OK);
    }

    @GetMapping("/search/name/{name}")
    public ResponseEntity<List<MovieDTO>> searchMoviesByName(@PathVariable(name = "name") String name) {
        List<MovieDTO> movies = movieService.searchByNameIgnoreCase(name);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid MovieEntity movieEntity) {
        movieService.register(movieEntity);
        return new ResponseEntity<>(MessageConstant.REGISTERED, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable(name = "id") Long id, @RequestBody MovieEntity updatedMovie) {
        movieService.update(id, updatedMovie);
        return new ResponseEntity<>(MessageConstant.UPDATED, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMovieById(@PathVariable(name = "id") Long id) {
        movieService.deleteMovieById(id);
        return new ResponseEntity<>(MessageConstant.DELETED, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MovieDTO>> getAllMovies() {
        return new ResponseEntity<>(movieService.getAllMovies(), HttpStatus.OK);
    }

    @GetMapping("/best")
    public ResponseEntity<List<MovieDTO>> getBestMovies() {
        return new ResponseEntity<>(movieService.getBestMovies(), HttpStatus.OK);
    }


}

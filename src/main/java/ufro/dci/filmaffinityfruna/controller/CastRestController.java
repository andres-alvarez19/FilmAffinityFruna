package ufro.dci.filmaffinityfruna.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufro.dci.filmaffinityfruna.model.dto.CastByActorDTO;
import ufro.dci.filmaffinityfruna.model.dto.CastByDirectorDTO;
import ufro.dci.filmaffinityfruna.model.dto.CastByMovieDTO;
import ufro.dci.filmaffinityfruna.model.entity.CastEntity;
import ufro.dci.filmaffinityfruna.service.CastService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cast")
public class CastRestController {

    private final CastService castService;

    @GetMapping
    public ResponseEntity<List<CastEntity>> getAllCasts() {
        List<CastEntity> casts = castService.getAllCasts();
        return new ResponseEntity<>(casts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CastEntity> getCastById(@PathVariable Long id) {
        Optional<CastEntity> cast = castService.getCastById(id);
        return cast.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                   .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/movie/{id}")
    public ResponseEntity<List<CastByMovieDTO>> getCastByMovieId(@PathVariable Long id) {
        List<CastByMovieDTO> casts = castService.getCastByMovieId(id);
        return new ResponseEntity<>(casts, HttpStatus.OK);
    }

    @GetMapping("/actor/{id}")
    public ResponseEntity<List<CastByActorDTO>> getCastByActorId(@PathVariable Long id) {
        List<CastByActorDTO> casts = castService.getCastByActorId(id);
        return new ResponseEntity<>(casts, HttpStatus.OK);
    }

    @GetMapping("/director/{id}")
    public ResponseEntity<List<CastByDirectorDTO>> getCastByDirectorId(@PathVariable Long id) {
        List<CastByDirectorDTO> casts = castService.getCastByDirectorId(id);
        return new ResponseEntity<>(casts, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CastEntity> createCast(@RequestBody CastEntity cast) {
        CastEntity savedCast = castService.saveCast(cast);
        return new ResponseEntity<>(savedCast, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCast(@PathVariable Long id) {
        castService.deleteCast(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
package ufro.dci.filmaffinityfruna.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ufro.dci.filmaffinityfruna.model.dto.CastByActorDTO;
import ufro.dci.filmaffinityfruna.model.dto.CastByDirectorDTO;
import ufro.dci.filmaffinityfruna.model.dto.CastByMovieDTO;
import ufro.dci.filmaffinityfruna.model.dto.CastDTO;
import ufro.dci.filmaffinityfruna.model.entity.CastEntity;
import ufro.dci.filmaffinityfruna.service.CastService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cast")
public class CastRestController {

    private final CastService castService;

    @GetMapping
    public ResponseEntity<List<CastDTO>> getAllCasts() {
        List<CastDTO> casts = castService.getAllCasts();
        return new ResponseEntity<>(casts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CastDTO> getCastById(@PathVariable Long id) {
        return new ResponseEntity<>(castService.getCastById(id), HttpStatus.OK);
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

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CastEntity> createCast(@RequestBody CastDTO cast) {
        CastEntity savedCast = castService.saveCast(cast);
        return new ResponseEntity<>(savedCast, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCast(@PathVariable Long id) {
        castService.deleteCast(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
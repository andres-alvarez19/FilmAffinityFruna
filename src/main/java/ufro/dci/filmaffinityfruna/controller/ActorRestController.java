package ufro.dci.filmaffinityfruna.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufro.dci.filmaffinityfruna.model.dto.ActorDTO;
import ufro.dci.filmaffinityfruna.model.dto.MovieDTO;
import ufro.dci.filmaffinityfruna.model.entity.ActorEntity;
import ufro.dci.filmaffinityfruna.service.ActorService;
import ufro.dci.filmaffinityfruna.utils.MessageConstant;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/actor")
public class ActorRestController {

    private final ActorService actorService;

    @GetMapping("/all")
    public ResponseEntity<List<ActorDTO>> listAll() {
        return new ResponseEntity<>(actorService.listAll(), HttpStatus.OK);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<ActorDTO> searchByIdPathVariable(@PathVariable(name = "id") Long id) {
        ActorDTO actor = actorService.searchById(id);
        return new ResponseEntity<>(actor, HttpStatus.OK);
    }


    @GetMapping("/search/name/{name}")
    public ResponseEntity<List<ActorDTO>> searchByNamePathVariable(@PathVariable(name = "name") String name) {
        List<ActorDTO> actors = actorService.searchByNameIgnoreCase(name);
        return new ResponseEntity<>(actors, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid ActorEntity actorEntity) {
        actorService.register(actorEntity);
        return new ResponseEntity<>(MessageConstant.REGISTERED, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable(name = "id") Long id, @RequestBody ActorEntity updatedActor) {
        actorService.update(id, updatedActor);
        return new ResponseEntity<>(MessageConstant.UPDATED, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteActorById(@PathVariable(name = "id") Long id) {
        actorService.deleteActorById(id);
        return new ResponseEntity<>(MessageConstant.DELETED, HttpStatus.OK);
    }

    @GetMapping("/{id}/movies")
    public ResponseEntity<List<MovieDTO>> getMoviesByActorId(@PathVariable(name = "id") Long id) {
        List<MovieDTO> movies = actorService.getMoviesByActorId(id);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }


}

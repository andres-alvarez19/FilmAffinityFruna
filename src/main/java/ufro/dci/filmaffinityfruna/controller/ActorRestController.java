package ufro.dci.filmaffinityfruna.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufro.dci.filmaffinityfruna.model.entity.ActorEntity;
import ufro.dci.filmaffinityfruna.service.ActorService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/actor")
public class ActorRestController {

    private final ActorService actorService;

    @GetMapping("/search")
    public ResponseEntity<List<ActorEntity>> searchByName(@RequestParam(name = "name") String name) {
        List<ActorEntity> actors = actorService.searchByName(name);
        return new ResponseEntity<>(actors, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid ActorEntity actorEntity) {
        actorService.register(actorEntity);
        return new ResponseEntity<>("Actor registrado correctamente", HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable(name = "id") Long id, @RequestBody ActorEntity updatedActor) {
        actorService.update(id, updatedActor);
        return new ResponseEntity<>("Actor actualizado correctamente", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteActorById(@PathVariable(name = "id") Long id) {
        actorService.deleteActorById(id);
        return new ResponseEntity<>("Actor eliminado correctamente", HttpStatus.OK);
    }
}

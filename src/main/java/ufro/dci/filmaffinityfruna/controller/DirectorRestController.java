package ufro.dci.filmaffinityfruna.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufro.dci.filmaffinityfruna.model.entity.DirectorEntity;
import ufro.dci.filmaffinityfruna.service.DirectorService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/director")
public class DirectorRestController {

    private final DirectorService directorService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody DirectorEntity directorEntity) {
        try {
            directorService.register(directorEntity);
            return new ResponseEntity<>("Director registrado correctamente", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Ocurrió un error " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable(name = "id") Long id, @RequestBody DirectorEntity updatedDirector) {
        try {
            directorService.update(id, updatedDirector);
            return new ResponseEntity<>("Director actualizado correctamente", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Ocurrió un error " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDirectorById(@PathVariable(name = "id") Long id) {
        try {
            directorService.deleteDirectorById(id);
            return new ResponseEntity<>("Director eliminado correctamente", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Ocurrió un error " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<DirectorEntity> searchByName(@PathVariable(name = "name") String name) {
        try {
            DirectorEntity director = directorService.searchByName(name);
            return new ResponseEntity<>(director, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
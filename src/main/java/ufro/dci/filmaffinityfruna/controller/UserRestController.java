package ufro.dci.filmaffinityfruna.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufro.dci.filmaffinityfruna.model.entity.UserEntity;
import ufro.dci.filmaffinityfruna.service.UserService;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserRestController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserEntity userEntity) {
        try {
            userService.register(userEntity);
            return new ResponseEntity<>("Usuario registrado correctamente", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Ocurrió un error " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

package ufro.dci.filmaffinityfruna.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ufro.dci.filmaffinityfruna.model.entity.UserEntity;
import ufro.dci.filmaffinityfruna.service.UserService;

@AllArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserEntity userEntity){
        if (userService.register(userEntity)){
            return ResponseEntity.ok("Usuario registrado correctamente");
        } else {
            return ResponseEntity.badRequest().body("El email ya está registrado");
        }
    }
}

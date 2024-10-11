package ufro.dci.filmaffinityfruna.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufro.dci.filmaffinityfruna.model.entity.UserEntity;
import ufro.dci.filmaffinityfruna.service.UserService;
import ufro.dci.filmaffinityfruna.utils.ResponseUtil;

import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserRestController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody UserEntity userEntity) {
        if (userService.register(userEntity)) {
            return ResponseUtil.createResponse(HttpStatus.OK, "Usuario registrado correctamente");
        } else {
            return ResponseUtil.createResponse(HttpStatus.BAD_REQUEST, "El email ya está registrado");
        }
    }

}

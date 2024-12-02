package ufro.dci.filmaffinityfruna.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufro.dci.filmaffinityfruna.model.entity.UserEntity;
import ufro.dci.filmaffinityfruna.service.UserService;
import ufro.dci.filmaffinityfruna.utils.MessageConstant;


@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserRestController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid UserEntity userEntity) {
        userService.register(userEntity);
        return new ResponseEntity<>(MessageConstant.REGISTERED, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUser(@PathVariable int id) {
        UserEntity user = userService.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}

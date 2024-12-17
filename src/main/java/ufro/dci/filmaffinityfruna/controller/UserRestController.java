package ufro.dci.filmaffinityfruna.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufro.dci.filmaffinityfruna.model.dto.LoginRequestDTO;
import ufro.dci.filmaffinityfruna.model.dto.LoginResponseDTO;
import ufro.dci.filmaffinityfruna.model.entity.UserEntity;
import ufro.dci.filmaffinityfruna.service.AuthService;
import ufro.dci.filmaffinityfruna.service.UserService;
import ufro.dci.filmaffinityfruna.utils.MessageConstant;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserRestController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid UserEntity userEntity) {
        UserEntity userRegistered = userService.register(userEntity);

        URI location = URI.create("/user/" + userRegistered.getId());
        return ResponseEntity.created(location).body(MessageConstant.REGISTERED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        return authService.login(loginRequestDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUser(@PathVariable long id) {
        UserEntity user = userService.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
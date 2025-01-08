package ufro.dci.filmaffinityfruna.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class LoginRequestDTO{

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

}
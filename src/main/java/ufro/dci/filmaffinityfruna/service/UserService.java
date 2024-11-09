package ufro.dci.filmaffinityfruna.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ufro.dci.filmaffinityfruna.model.entity.UserEntity;
import ufro.dci.filmaffinityfruna.repository.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final ValidationService validationService; // Inyectamos el ValidationService

    public void register(UserEntity userEntity) {
        // Delegamos la validación al ValidationService
        if (validationService.existsUserByEmail(userEntity.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        } else {
            userRepository.save(userEntity);
        }
    }

    public void update(Long id, UserEntity modifiedUser) {
        // Validamos si el email ya está registrado antes de actualizar
        if (validationService.existsUserByEmail(modifiedUser.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        } else {
            Optional<UserEntity> optionalUser = userRepository.findById(id);
            if (optionalUser.isPresent()) {
                UserEntity user = optionalUser.get();
                user.setUsername(modifiedUser.getUsername());
                user.setEmail(modifiedUser.getEmail());
                userRepository.save(user);
            } else {
                throw new IllegalArgumentException("Usuario no encontrado");
            }
        }
    }

    public void deleteUserById(Long id) {
        // Validamos si el usuario existe antes de eliminarlo
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuario no encontrado");
        } else {
            userRepository.deleteById(id);
        }
    }

    public UserEntity searchByName(String name) {
        // Validamos si el usuario existe por nombre
        if (!validationService.existsUserByUsername(name)) {
            throw new IllegalArgumentException("Usuario no encontrado");
        } else {
            return userRepository.findByUsername(name);
        }
    }

    public UserEntity findById(Long id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
    }
}

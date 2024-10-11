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

    public void register(UserEntity userEntity) {
        if (userRepository.existsByEmail(userEntity.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        } else {
            userRepository.save(userEntity);
        }
    }

    public void update(Long id, UserEntity modifiedUser) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        if (userRepository.existsByEmail(modifiedUser.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }else{
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
        if(!userRepository.existsById(id)){
            throw new IllegalArgumentException("Usuario no encontrado");
        }else{
            userRepository.deleteById(id);
        }
    }

    public UserEntity searchByName(String name) {
        if(!userRepository.existsByUsername(name)){
            throw new IllegalArgumentException("Usuario no encontrado");
        }else{
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

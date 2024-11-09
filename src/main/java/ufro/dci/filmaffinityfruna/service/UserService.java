package ufro.dci.filmaffinityfruna.service;

import org.springframework.stereotype.Service;
import ufro.dci.filmaffinityfruna.model.entity.UserEntity;
import ufro.dci.filmaffinityfruna.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService extends BaseService<UserEntity, Long> {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    @Override
    public void register(UserEntity userEntity) {
        if (userRepository.existsByEmail(userEntity.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        super.register(userEntity);
    }

    @Override
    public void update(Long id, UserEntity modifiedUser) {
        if (userRepository.existsByEmail(modifiedUser.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        super.update(id, modifiedUser);
    }

    @Override
    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        super.deleteById(id);
    }

    public UserEntity searchByName(String name) {
        if (!userRepository.existsByUsername(name)) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        return userRepository.findByUsername(name);
    }

    public UserEntity findById(Long id) {
        return super.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }
}

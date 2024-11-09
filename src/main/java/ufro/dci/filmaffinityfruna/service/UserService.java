package ufro.dci.filmaffinityfruna.service;

import org.springframework.stereotype.Service;
import ufro.dci.filmaffinityfruna.model.entity.UserEntity;
import ufro.dci.filmaffinityfruna.repository.UserRepository;

@Service
public class UserService extends BaseService<UserEntity, Long> {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    @Override
    public boolean existsByUniqueProperty(UserEntity userEntity) {
        return userRepository.existsByEmail(userEntity.getEmail());
    }

    @Override
    public void register(UserEntity userEntity) {
        if (existsByUniqueProperty(userEntity)) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        super.register(userEntity);
    }

    public UserEntity searchByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }

    public void deleteByUsername(String username) {
        if (!userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        userRepository.deleteByUsername(username);
    }
}

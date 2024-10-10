package ufro.dci.filmaffinityfruna.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufro.dci.filmaffinityfruna.model.entity.UserEntity;
import ufro.dci.filmaffinityfruna.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean register(UserEntity userEntity) {
        if (userRepository.existsByEmail(userEntity.getEmail())) {
            return false;
        } else {
            userRepository.save(userEntity);
            return true;
        }
    }
}

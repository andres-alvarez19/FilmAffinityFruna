package ufro.dci.filmaffinityfruna.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ufro.dci.filmaffinityfruna.model.entity.RoleEntity;
import ufro.dci.filmaffinityfruna.model.entity.UserEntity;
import ufro.dci.filmaffinityfruna.repository.UserRepository;
import ufro.dci.filmaffinityfruna.utils.MessageConstant;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public UserEntity register(UserEntity userEntity) {
        if (userRepository.existsByEmail(userEntity.getEmail())) {
            throw new DataIntegrityViolationException(MessageConstant.EMAIL_REGISTERED);
        } else {
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            if (!roleService.existsByRoleName("ROLE_ADMIN")) {
                addRole(userEntity, "ROLE_ADMIN");
            }
            RoleEntity role = roleService.findRoleByName("ROLE_USER");
            if (!userEntity.getRoles().contains(role)) {
                addRole(userEntity, "ROLE_USER");
            }
            return userRepository.save(userEntity);
        }
    }

    private void addRole(UserEntity userEntity, String roleName) {
        RoleEntity role = roleService.findRoleByName(roleName);
        userEntity.getRoles().add(role);
    }

    public void update(long id, UserEntity modifiedUser) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        if (userRepository.existsByEmail(modifiedUser.getEmail())) {
            throw new IllegalArgumentException(MessageConstant.EMAIL_REGISTERED);
        } else {
            if (optionalUser.isPresent()) {
                UserEntity user = optionalUser.get();
                user.setUsername(modifiedUser.getUsername());
                user.setEmail(modifiedUser.getEmail());
                userRepository.save(user);
            } else {
                throw new IllegalArgumentException(MessageConstant.USER_NOT_FOUND);
            }
        }
    }

    public void deleteUserById(long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException(MessageConstant.USER_NOT_FOUND);
        } else {
            userRepository.deleteById(id);
        }
    }

    public UserEntity searchByName(String name) {
        Optional<UserEntity> optionalUser = userRepository.findByUsername(name);
        return optionalUser.orElseThrow(() -> new IllegalArgumentException(MessageConstant.USER_NOT_FOUND));
    }

    public UserEntity findById(long id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        return optionalUser.orElseThrow(() -> new IllegalArgumentException(MessageConstant.USER_NOT_FOUND));
    }

    public void addRole(String roleName, long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(MessageConstant.USER_NOT_FOUND));

        RoleEntity role = roleService.findRoleByName(roleName);

        user.getRoles().add(role);
    }
}
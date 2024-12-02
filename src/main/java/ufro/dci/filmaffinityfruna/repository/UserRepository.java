package ufro.dci.filmaffinityfruna.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ufro.dci.filmaffinityfruna.model.entity.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmailAndPassword(String email, String password);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findById(long id);
    boolean existsByEmail(String email);
    boolean existsByUsername(String name);
    boolean existsById(long id);
    void deleteById(long id);
}

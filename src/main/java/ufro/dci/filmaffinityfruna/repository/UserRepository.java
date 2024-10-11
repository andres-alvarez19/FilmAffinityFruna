package ufro.dci.filmaffinityfruna.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ufro.dci.filmaffinityfruna.model.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);
    UserEntity findByEmailAndPassword(String email, String password);
    UserEntity findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUsername(String name);
}

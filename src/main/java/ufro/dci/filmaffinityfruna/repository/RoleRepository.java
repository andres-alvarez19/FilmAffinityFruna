package ufro.dci.filmaffinityfruna.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ufro.dci.filmaffinityfruna.model.entity.RoleEntity;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Integer> {

    Optional<RoleEntity> findByName(String name);
    boolean existsByName(String name);
}

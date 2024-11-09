package ufro.dci.filmaffinityfruna.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class BaseService<T, ID> {

    protected final JpaRepository<T, ID> repository;

    protected BaseService(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    public void register(T entity) {
        repository.save(entity);
    }

    public void update(ID id, T updatedEntity) {
        Optional<T> optionalEntity = repository.findById(id);
        if (optionalEntity.isPresent()) {
            repository.save(updatedEntity);
        } else {
            throw new IllegalArgumentException("Entidad no encontrada");
        }
    }

    public void deleteById(ID id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Entidad no encontrada");
        } else {
            repository.deleteById(id);
        }
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }
}

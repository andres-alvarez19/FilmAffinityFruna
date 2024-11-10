package ufro.dci.filmaffinityfruna.service;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public abstract class BaseService<T, ID> {

    protected final JpaRepository<T, ID> repository;

    protected BaseService(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    // Método genérico de registro
    public void register(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entidad no puede ser nula");
        }
        repository.save(entity);
    }

    // Método genérico de actualización
    public void update(ID id, T updatedEntity) {
        if (updatedEntity == null) {
            throw new IllegalArgumentException("Entidad no puede ser nula");
        }
        Optional<T> existingEntity = repository.findById(id);
        if (existingEntity.isPresent()) {
            repository.save(updatedEntity);
        } else {
            throw new EntityNotFoundException("Entidad no encontrada con id: " + id);
        }
    }

    // Método genérico de eliminación por ID
    public void deleteById(ID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Entidad no encontrada con id: " + id);
        }
        repository.deleteById(id);
    }

    // Método genérico para buscar por ID
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    // Método genérico para obtener todos los registros
    public List<T> findAll() {
        return repository.findAll();
    }

    // Método abstracto para la verificación de existencia basado en una propiedad específica,
    // que implementarán las subclases si es necesario.
    public abstract boolean existsByUniqueProperty(T entity);
}

package ufro.dci.filmaffinityfruna.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor

@Entity
@Table(name = "genero")
public class GenreEntity {

    @Id
    @Column(name="nombre", nullable = false, unique = true, length = 45)
    @NotEmpty(message = "El nombre del género no puede estar vacío")
    private String name;

    @Column(name="descripcion")
    private String description;

    @OneToMany(mappedBy = "genre")
    private Set<MovieEntity> movies = new HashSet<>();
}

package ufro.dci.filmaffinityfruna.model.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "director")
public class DirectorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_director", nullable = false)
    private int id;

    @Column(name="nombre", nullable = false)
    private String name;

    @Column(name="pais", nullable = false)
    private String nationality;

    @Column(name="fecha_nacimiento", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name="fecha_defuncion", nullable = true)
    private LocalDate dateOfDeath;

    @Column(name="enlace_wiki", nullable = true)
    private String wikipediaLink;

    @OneToMany(mappedBy = "director")
    private Set<MovieEntity> moviesDirected = new HashSet<>();
}

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
@Table(name = "actor")
public class ActorEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id_actor")
    private int id;

    @Column(name="nombre", nullable = false)
    private String name;

    @Column(name="pais", nullable = false)
    private String nationality;

    @Column(name="fecha_nacimiento", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name="fecha_defuncion")
    private LocalDate dateOfDeath;

    @Column(name="enlace_wiki")
    private String wikipediaLink;

    @OneToMany(mappedBy = "actor")
    private Set<CastEntity> charactersPlayed = new HashSet<>();
}
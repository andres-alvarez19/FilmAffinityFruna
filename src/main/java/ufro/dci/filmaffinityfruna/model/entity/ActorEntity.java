package ufro.dci.filmaffinityfruna.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "ID no puede ser nulo")
    private long id;

    @Column(name="nombre", nullable = false)
    @NotNull(message = "Nombre no puede ser nulo")
    private String name;

    @Column(name="pais", nullable = false)
    @NotNull(message = "Nacionalidad no puede ser nula")
    private String nationality;

    @Column(name="fecha_nacimiento", nullable = false)
    @NotNull(message = "Fecha de nacimiento no puede ser nula")
    private LocalDate dateOfBirth;

    @Column(name="fecha_defuncion")
    private LocalDate dateOfDeath;

    @Column(name="enlace_wiki")
    private String wikipediaLink;

    @Column(name="imagen")
    private String photoUrl;

    @OneToMany(mappedBy = "actor")
    private Set<CastEntity> charactersPlayed = new HashSet<>();
}
package ufro.dci.filmaffinityfruna.model.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "director")
public class DirectorEntity {

    @Id
    @Column(name="id_director", nullable = false)
    private Long id;

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
}

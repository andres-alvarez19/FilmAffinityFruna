package ufro.dci.filmaffinityfruna.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor

@Entity
@Table(name = "pelicula")
public class MovieEntity {
    @Id
    @Column(name="id_pelicula")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "ID no puede ser nulo")
    private long id;

    @Column(name="nombre", nullable = false)
    @NotNull(message = "Nombre no puede ser nulo")
    private String name;

    @ManyToOne
    @JoinColumn(name = "director_id_director", nullable = false)
    @NotNull(message = "Director no puede ser nulo")
    private DirectorEntity director;

    @Column(name="sinopsis", nullable = true)
    private String synopsis;

    @Column(name="pais")
    private String country;

    @Column(name="duracion", nullable = false)
    @NotNull(message = "Duración no puede ser nula")
    private LocalTime duration;

    @Column(name="estreno", nullable = false)
    @NotNull(message = "Año de estreno no puede ser nulo")
    private LocalDate releaseYear;

    @Column(name="enlace_wiki")
    private String wikipediaLink;

    @ManyToOne
    @JoinColumn(name = "genero_nombre", nullable = false, referencedColumnName = "nombre")
    @NotNull(message = "Género no puede ser nulo")
    private GenreEntity genre;

    @ManyToMany(mappedBy = "favoriteMovies")
    private Set<UserEntity> usersWhoFavorited = new HashSet<>();

    @OneToMany(mappedBy = "movie")
    private Set<CastEntity> cast = new HashSet<>();
}
package ufro.dci.filmaffinityfruna.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDate;
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
    private long id;

    @Column(name="nombre", nullable = false)
    private String name;

    @Column(name="sinopsis", nullable = true)
    private String synopsis;

    @ManyToOne
    @JoinColumn(name = "id_director", nullable = false)
    private DirectorEntity director;

    @Column(name="pais", nullable = true)
    private String country;

    @Column(name="duracion", nullable = false)
    private Time duration;

    @Column(name="estreno", nullable = false)
    private LocalDate releaseYear;

    @Column(name="enlace_wiki", nullable = true)
    private String wikipediaLink;

    @ManyToOne
    @JoinColumn(name = "nombre", nullable = false)
    private GenreEntity genre;

    @OneToMany
    @JoinColumn(name = "pelicula_id_pelicula", nullable = false)
    private Set<CastEntity> cast = new HashSet<>();

    @ManyToMany(mappedBy = "favoriteMovies")
    private Set<UserEntity> usersWhoFavorited= new HashSet<>();

}

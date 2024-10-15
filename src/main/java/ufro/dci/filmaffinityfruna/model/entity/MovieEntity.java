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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="nombre", nullable = false)
    private String name;

    @Column(name="sinopsis", nullable = true)
    private String synopsis;

    @ManyToOne
    @JoinColumn(name = "director_id_director", nullable = false)
    private DirectorEntity director;

    @Column(name="pais" )
    private String country;

    @Column(name="duracion", nullable = false)
    private Time duration;

    @Column(name="estreno", nullable = false)
    private LocalDate releaseYear;

    @Column(name="enlace_wiki")
    private String wikipediaLink;

    @ManyToOne
    @JoinColumn(name = "genero_nombre", nullable = false, referencedColumnName = "nombre")
    private GenreEntity genre;

    @ManyToMany(mappedBy = "favoriteMovies")
    private Set<UserEntity> usersWhoFavorited= new HashSet<>();

    @OneToMany(mappedBy = "movie")
    private Set<CastEntity> cast = new HashSet<>();
}

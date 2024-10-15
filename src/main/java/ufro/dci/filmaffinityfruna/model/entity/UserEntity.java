package ufro.dci.filmaffinityfruna.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "usuario")
public class UserEntity {
    @Id
    @Column(name = "id_usuario")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(name = "nombre", nullable = false)
    private String username;

    @Column(name = "correo", nullable = false, unique = true)
    private String email;

    @Column(name = "contrasennia", nullable = false)
    private String password;

    @ManyToMany
    @JoinTable(
            name = "usuario_has_pelicula",
            joinColumns = @JoinColumn(name = "usuario_id_usuario", referencedColumnName = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "pelicula_id_pelicula", referencedColumnName = "id_pelicula")
    )
    private Set<MovieEntity> favoriteMovies = new HashSet<>();
}

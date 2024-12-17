package ufro.dci.filmaffinityfruna.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "ID no puede ser nulo")
    private long id;

    @Column(name = "nombre", nullable = false, length = 45)
    @NotNull(message = "Nombre de usuario no puede ser nulo")
    private String username;

    @Column(name = "correo", nullable = false, unique = true, length = 45)
    @NotNull(message = "Correo no puede ser nulo")
    private String email;

    @Column(name = "contrasennia", nullable = false)
    @NotNull(message = "Contraseña no puede ser nula")
    private String password;

    @ManyToMany
    @JoinTable(
            name = "peliculas_vistas",
            joinColumns = @JoinColumn(name = "usuario_id_usuario", referencedColumnName = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "pelicula_id_pelicula", referencedColumnName = "id_pelicula")
    )
    private Set<MovieEntity> favoriteMovies = new HashSet<>();
}
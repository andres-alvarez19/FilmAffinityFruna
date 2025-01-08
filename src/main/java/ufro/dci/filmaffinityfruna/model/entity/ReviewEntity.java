package ufro.dci.filmaffinityfruna.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor

@Entity
@Table(name = "review")
public class ReviewEntity {

    @Id
    @Column(name = "id_review")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "ID de review no puede ser nulo")
    private long id;

    @Column(name = "description", nullable = false, length = 45)
    @NotNull(message = "Descripcion no puede ser nula")
    private String description;

    @Column(name = "rating")
    @NotNull(message = "Rating no puede ser nulo")
    private Float rating;

    @Column(name = "review_date", nullable = false)
    @NotNull(message = "Fecha no puede ser nula")
    private LocalDate reviewDate;

    @ManyToOne
    @JoinColumn(name = "pelicula_id_pelicula", nullable = false, referencedColumnName = "id_pelicula")
    @NotNull(message = "Pelicula no puede ser nula")
    private MovieEntity movie;

    @ManyToOne
    @JoinColumn(name = "usuario_id_usuario", nullable = false, referencedColumnName = "id_usuario")
    @NotNull(message = "Usuario no puede ser nulo")
    private UserEntity user;
}

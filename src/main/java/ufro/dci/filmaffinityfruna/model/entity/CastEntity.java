package ufro.dci.filmaffinityfruna.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor

@Entity
@Table(name = "reparto")
public class CastEntity {

    @Id
    @Column(name = "id_reparto")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "ID no puede ser nulo")
    private long id;

    @Column(name = "nombre_personaje", nullable = false)
    @NotNull(message = "Nombre del personaje no puede ser nulo")
    private String characterName;

    @ManyToOne
    @JoinColumn(name = "actor_id_actor", nullable = false)
    @NotNull(message = "Actor no puede ser nulo")
    private ActorEntity actor;

    @ManyToOne
    @JoinColumn(name = "pelicula_id_pelicula", nullable = false)
    @NotNull(message = "Película no puede ser nula")
    private MovieEntity movie;

}
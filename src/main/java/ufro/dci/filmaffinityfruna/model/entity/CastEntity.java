package ufro.dci.filmaffinityfruna.model.entity;

import jakarta.persistence.*;
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
    private int id;

    @Column(name = "nombre_personaje", nullable = false)
    private String characterName;

    @ManyToOne
    @JoinColumn(name = "actor_id_actor", nullable = false)
    private ActorEntity actor;

    @ManyToOne
    @JoinColumn(name = "pelicula_id_pelicula", nullable = false)
    private MovieEntity movie;

}

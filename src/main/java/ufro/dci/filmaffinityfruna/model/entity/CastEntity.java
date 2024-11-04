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

    public String getName() {
        return actor != null ? actor.getName() : null;
    }

    public void setName(String name) {
        this.characterName = name;
    }

    @Override
    public String toString() {
        return "CastEntity{" +
                "id=" + id +
                ", characterName='" + characterName + '\'' +
                ", actor=" + (actor != null ? actor.getName() : "null") +
                '}';
    }

    public CastEntity(int id, String characterName, ActorEntity actor, MovieEntity movie) {
        this.id = id;
        this.characterName = characterName;
        this.actor = actor;
        this.movie = movie;
    }

}

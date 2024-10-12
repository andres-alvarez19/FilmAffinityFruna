package ufro.dci.filmaffinityfruna.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor

@Entity
@Table(name = "genero")
public class GenreEntity {

    @Id
    @Column(name="id_genero", nullable = false)
    private String name;

    @Column(name="descripcion", nullable = true )
    private String description;

}

package ufro.dci.filmaffinityfruna.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity

@Table(name = "usuario")
public class UserEntity {
    @Id
    @Column(name = "id_usuario")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    @Column(name = "nombre", nullable = false)
    private String username;
    @Column(name = "correo", nullable = false, unique = true)
    private String email;
    @Column(name = "contrasennia", nullable = false)
    private String password;
}

package com.ciss.banco.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idUsuairo;
    private String usuario;
    private String senha;
    private String nome;
    private String email;
    private Boolean ativo;

    @Override
    public int hashCode() {
        return Objects.hash(idUsuairo, usuario);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Usuario u = (Usuario) obj;
        return idUsuairo.equals(u.idUsuairo) && usuario.equals(u.usuario);
    }

}

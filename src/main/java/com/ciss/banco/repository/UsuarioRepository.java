package com.ciss.banco.repository;

import com.ciss.banco.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByUsuarioOrEmail(String usuario, String email);

}

package com.ciss.banco.service;

import com.ciss.banco.model.Usuario;

import java.util.List;

public interface UsuarioService {

    List<Usuario> getAll();

    Usuario getById(Integer id);

    Usuario create(Usuario toSave);

    Usuario update(Usuario toUpdate);
}

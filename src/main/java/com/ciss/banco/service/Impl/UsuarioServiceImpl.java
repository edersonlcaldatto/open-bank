package com.ciss.banco.service.Impl;

import com.ciss.banco.model.Usuario;
import com.ciss.banco.repository.UsuarioRepository;
import com.ciss.banco.service.UsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioRepository usuarioRepository;

    private PasswordEncoder bcryptEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder bcryptEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.bcryptEncoder = bcryptEncoder;
    }

    @Override
    public List<Usuario> getAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario getById(Integer id) {
      return usuarioRepository.findById(id)
                .orElseThrow( () -> new NoResultException(String.format("Usuário de código %d não localizado", id)));
    }

    @Override
    public Usuario create(Usuario toSave) {
        toSave.setSenha(bcryptEncoder.encode(toSave.getSenha()));
        return usuarioRepository.save(toSave);
    }

    @Override
    public Usuario update(Usuario toUpdate) {
        if (!usuarioRepository.existsById(toUpdate.getIdUsuairo())){
            throw new NoResultException(String.format("Grupo de código %d não localizado", toUpdate.getIdUsuairo()));
        }
        return usuarioRepository.save(toUpdate);
    }
}

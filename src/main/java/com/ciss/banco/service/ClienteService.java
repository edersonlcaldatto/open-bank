package com.ciss.banco.service;

import com.ciss.banco.model.Cliente;

import java.util.List;

public interface ClienteService {

    Cliente create(Cliente cliente) throws Exception;

    Cliente getOne(Long clienteId);

    List<Cliente> findAll();

    void deleteById(Long clienteId);

    Cliente update(Cliente cliente);

}

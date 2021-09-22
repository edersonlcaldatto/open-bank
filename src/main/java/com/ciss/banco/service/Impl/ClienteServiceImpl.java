package com.ciss.banco.service.Impl;

import com.ciss.banco.model.Cliente;
import com.ciss.banco.repository.ClienteRepository;
import com.ciss.banco.service.ClienteService;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Cliente create(Cliente cliente) throws Exception {
        if(clienteRepository.existsByDocumento(cliente.getDocumento())){
            throw new Exception(String.format("Cliente ja cadastrado com o documento %s", cliente.getDocumento()));
        }
        Cliente clientePersist = clienteRepository.save(cliente);
        return clientePersist;
    }

    @Override
    public Cliente getOne(Long clienteId) {
        return clienteRepository.findById(clienteId)
                .orElseThrow(() -> new NoResultException(String.format("Cliente com codigo %d nao encontrado", clienteId)));
    }

    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    @Override
    public void deleteById(Long clienteId) {
        if (!clienteRepository.existsById(clienteId)){
            throw new NoResultException(String.format("Cliente com código %d não encontrado", clienteId));
        }
        clienteRepository.deleteById(clienteId);
    }

    @Override
    public Cliente update(Cliente cliente) {
        if (!clienteRepository.existsById(cliente.getId())){
            throw new NoResultException(String.format("Cliente com código %d não encontrado", cliente.getId()));
        }
        return clienteRepository.save(cliente);
    }
}

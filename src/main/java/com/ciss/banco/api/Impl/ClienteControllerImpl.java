package com.ciss.banco.api.Impl;

import com.ciss.banco.api.ClienteController;
import com.ciss.banco.dto.request.ClienteRequestDto;
import com.ciss.banco.mapper.ClienteMapper;
import com.ciss.banco.model.Cliente;
import com.ciss.banco.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteControllerImpl implements ClienteController {

    private final ClienteService clienteService;
    private final ClienteMapper clienteMapper;

    public ClienteControllerImpl(ClienteService clienteService, ClienteMapper clienteMapper) {
        this.clienteService = clienteService;
        this.clienteMapper = clienteMapper;
    }

    @Override
    public ResponseEntity<List<Cliente>> getAll() {
        return ResponseEntity.ok(clienteService.findAll());
    }

    @Override
    public ResponseEntity<Cliente> getById(Long clienteId) {
        return ResponseEntity.ok(clienteService.getOne(clienteId));
    }

    @Override
    public ResponseEntity<Cliente> create(ClienteRequestDto clienteDto) throws Exception {
        Cliente toReturn = clienteService.create(clienteMapper.toEntity(clienteDto));
        return ResponseEntity.created(URI.create("/cliente/" + toReturn.getId())).body(toReturn);
    }

    @Override
    public ResponseEntity<Cliente> update(long clienteId, ClienteRequestDto clienteDto) {
        return ResponseEntity.ok(clienteService.update(clienteMapper.toEntity(clienteDto, clienteId)));
    }

    @Override
    public ResponseEntity<Void> deleteById(Long clienteId) {
        clienteService.deleteById(clienteId);
        return ResponseEntity.noContent().build();
    }
}

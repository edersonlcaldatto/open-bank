package com.ciss.banco.mapper;

import com.ciss.banco.dto.request.ClienteRequestDto;
import com.ciss.banco.model.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public Cliente toEntity(ClienteRequestDto dto){
        Cliente cliente = new Cliente();
        cliente.setDocumento(dto.getDocumento());
        cliente.setNome(dto.getNome());

        return cliente;
    }

    public Cliente toEntity(ClienteRequestDto dto, Long idCliente){
        Cliente cliente = new Cliente();
        cliente.setId(idCliente);
        cliente.setDocumento(dto.getDocumento());
        cliente.setNome(dto.getNome());

        return cliente;
    }



}

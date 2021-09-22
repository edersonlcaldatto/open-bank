package com.ciss.banco.cliente.mock;

import com.ciss.banco.dto.request.ClienteRequestDto;

public class ClienteDtoMock {

    public static ClienteRequestDto clienteDto1(){
        ClienteRequestDto clienteDto = new ClienteRequestDto();
        clienteDto.setNome("Cliente 1");
        clienteDto.setDocumento("123456789");
        return clienteDto;
    }

    public static ClienteRequestDto clienteDto2(){
        ClienteRequestDto clienteDto = new ClienteRequestDto();
        clienteDto.setNome("Cliente 2");
        clienteDto.setDocumento("987654321");
        return clienteDto;
    }
}

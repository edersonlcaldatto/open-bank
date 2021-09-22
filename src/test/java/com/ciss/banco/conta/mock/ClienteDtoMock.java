package com.ciss.banco.conta.mock;

import com.ciss.banco.dto.request.ClienteRequestDto;

public class ClienteDtoMock {

    public static ClienteRequestDto getOne(){
        ClienteRequestDto clienteDto = new ClienteRequestDto();
        clienteDto.setNome("Cliente 1");
        clienteDto.setDocumento("123456789");
        return clienteDto;
    }

}

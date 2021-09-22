package com.ciss.banco.conta.mock;

import com.ciss.banco.dto.request.ContaRequestDto;

import java.math.BigDecimal;

public class ContaDtoMock {

    public static ContaRequestDto getOne(){
        ContaRequestDto dto = new ContaRequestDto();
        dto.setAgencia("123");
        dto.setNumeroConta("789997-1");
        dto.setIdCliente(1L);
        dto.setSaldoInicial(new BigDecimal(1000.00));

        return dto;
    }

}

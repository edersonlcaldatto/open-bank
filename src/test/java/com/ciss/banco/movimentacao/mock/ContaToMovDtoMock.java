package com.ciss.banco.movimentacao.mock;

import com.ciss.banco.dto.request.ContaRequestDto;

import java.math.BigDecimal;

public class ContaToMovDtoMock {

    public static ContaRequestDto contaDto1(){
        ContaRequestDto dto = new ContaRequestDto();
        dto.setAgencia("123");
        dto.setNumeroConta("88888-1");
        dto.setIdCliente(1L);
        dto.setSaldoInicial(new BigDecimal(1000.00));

        return dto;
    }

    public static ContaRequestDto contaDto2(){
        ContaRequestDto dto = new ContaRequestDto();
        dto.setAgencia("456");
        dto.setNumeroConta("999999-2");
        dto.setIdCliente(2L);
        dto.setSaldoInicial(new BigDecimal(500.00));

        return dto;
    }



}

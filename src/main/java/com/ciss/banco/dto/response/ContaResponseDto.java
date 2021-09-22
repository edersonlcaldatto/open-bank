package com.ciss.banco.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ContaResponseDto {

    private Long id;
    private String agencia;
    private String numeroConta;
    private BigDecimal saldo;
    private ClienteSemContasDto cliente;

}

package com.ciss.banco.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ContaRequestDto {

    @NotNull(message = "Código do cliente é obrigatório")
    private Long idCliente;
    @NotEmpty(message = "Informe a Agência")
    private String agencia;
    @NotEmpty(message = "Informe o número da conta")
    private String numeroConta;
    private BigDecimal saldoInicial;

}

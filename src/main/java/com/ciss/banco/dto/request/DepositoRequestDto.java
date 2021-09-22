package com.ciss.banco.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepositoRequestDto {

    @NotNull
    private Long idContaDestino;
    @NotNull
    private BigDecimal valor;
}

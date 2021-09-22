package com.ciss.banco.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaqueRequestDto {

    @NotNull
    private Long idContaOrigem;
    @NotNull
    private BigDecimal valor;

}

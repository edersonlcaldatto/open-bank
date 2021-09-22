package com.ciss.banco.dto.response;

import com.ciss.banco.model.Conta;
import com.ciss.banco.model.enuns.DebitoCredito;
import com.ciss.banco.model.enuns.TipoMovimentacao;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class MovimentacaoResponseDto {

    private Long id;
    private Conta contaOrigem;
    private Conta contaDestino;
    private TipoMovimentacao tipoMovimentacao;
    private DebitoCredito debitoCredito;
    private BigDecimal valor;

}

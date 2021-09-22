package com.ciss.banco.model;

import com.ciss.banco.model.enuns.DebitoCredito;
import com.ciss.banco.model.enuns.TipoMovimentacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "movimentacao")
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_origem_id")
    private Conta contaOrigem;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_destino_id")
    private Conta contaDestino;
    @Enumerated(EnumType.STRING)
    private TipoMovimentacao tipoMovimentacao;
    @Enumerated(EnumType.STRING)
    private DebitoCredito debitoCredito;
    @NotNull
    private BigDecimal valor;

}

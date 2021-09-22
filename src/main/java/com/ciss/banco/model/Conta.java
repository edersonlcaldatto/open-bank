package com.ciss.banco.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "conta")
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String agencia;
    @NotBlank
    private String numeroConta;
    private BigDecimal saldo;

    @JsonBackReference
    @ManyToOne
    private Cliente cliente;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Movimentacao> movimentacoes;

}

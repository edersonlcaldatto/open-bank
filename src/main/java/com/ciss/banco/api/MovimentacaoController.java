package com.ciss.banco.api;

import com.ciss.banco.dto.request.DepositoRequestDto;
import com.ciss.banco.dto.request.SaqueRequestDto;
import com.ciss.banco.dto.request.TransferenciaRequestDto;
import com.ciss.banco.model.Movimentacao;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

public interface MovimentacaoController {

    @Operation(summary = "Retorna a lista de Movimentações com base na Conta.")
    @GetMapping("/{id-conta}")
    ResponseEntity<List<Movimentacao>> getMovimentacaoByConta(@PathVariable("id-conta") Long contaId);

    @Operation(summary = "Gera uma movimentação de depósito")
    @PostMapping("/depositar")
    ResponseEntity<Movimentacao> depositar(@Valid @RequestBody DepositoRequestDto depositoDto);

    @Operation(summary = "Gera uma movimentação de Saque")
    @PostMapping("/sacar")
    ResponseEntity<Movimentacao> sacar(@Valid @RequestBody SaqueRequestDto saqueDto) throws Exception;

    @Operation(summary = "Gera uma transferência entre as contas")
    @PostMapping("/transferir")
    ResponseEntity<Movimentacao> transferir(@Valid @RequestBody TransferenciaRequestDto transferenciaDto) throws Exception;

}

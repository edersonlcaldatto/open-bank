package com.ciss.banco.service;

import com.ciss.banco.dto.request.DepositoRequestDto;
import com.ciss.banco.dto.request.SaqueRequestDto;
import com.ciss.banco.dto.request.TransferenciaRequestDto;
import com.ciss.banco.model.Movimentacao;

import java.util.List;

public interface MovimentacaoService {

    List<Movimentacao> getAllByConta(Long contaId);

    Movimentacao inicializaSaldo(Movimentacao movimentacao);

    Movimentacao transferir(TransferenciaRequestDto transferenciaDto) throws Exception;

    Movimentacao depositar(DepositoRequestDto depositoDto);

    Movimentacao sacar(SaqueRequestDto saqueDto) throws Exception;
}

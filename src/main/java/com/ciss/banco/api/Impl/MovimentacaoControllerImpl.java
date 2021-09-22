package com.ciss.banco.api.Impl;

import com.ciss.banco.api.MovimentacaoController;
import com.ciss.banco.dto.request.DepositoRequestDto;
import com.ciss.banco.dto.request.SaqueRequestDto;
import com.ciss.banco.dto.request.TransferenciaRequestDto;
import com.ciss.banco.model.Movimentacao;
import com.ciss.banco.service.MovimentacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/movimentacao")
public class MovimentacaoControllerImpl implements MovimentacaoController {

    private final MovimentacaoService movimentacaoService;

    public MovimentacaoControllerImpl(MovimentacaoService movimentacaoService) {
        this.movimentacaoService = movimentacaoService;
    }

    @Override
    public ResponseEntity<List<Movimentacao>> getMovimentacaoByConta(Long contaId) {
        return ResponseEntity.ok(movimentacaoService.getAllByConta(contaId));
    }

    @Override
    public ResponseEntity<Movimentacao> transferir(TransferenciaRequestDto transferenciaDto) throws Exception {
        Movimentacao toReturn = movimentacaoService.transferir(transferenciaDto);
        return ResponseEntity.created(URI.create("/movimentacao/" + toReturn.getId())).body(toReturn);
    }

    @Override
    public ResponseEntity<Movimentacao> depositar(DepositoRequestDto depositoDto) {
        Movimentacao toReturn = movimentacaoService.depositar(depositoDto);
        return ResponseEntity.created(URI.create("/movimentacao/" + toReturn.getId())).body(toReturn);

    }

    @Override
    public ResponseEntity<Movimentacao> sacar(SaqueRequestDto saqueDto) throws Exception {
        Movimentacao toReturn = movimentacaoService.sacar(saqueDto);
        return ResponseEntity.created(URI.create("/movimentacao/" + toReturn.getId())).body(toReturn);
    }

}

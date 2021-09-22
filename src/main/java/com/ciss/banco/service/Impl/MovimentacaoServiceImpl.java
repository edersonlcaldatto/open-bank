package com.ciss.banco.service.Impl;

import com.ciss.banco.dto.request.DepositoRequestDto;
import com.ciss.banco.dto.request.SaqueRequestDto;
import com.ciss.banco.dto.request.TransferenciaRequestDto;
import com.ciss.banco.model.Conta;
import com.ciss.banco.model.Movimentacao;
import com.ciss.banco.model.enuns.DebitoCredito;
import com.ciss.banco.model.enuns.TipoMovimentacao;
import com.ciss.banco.repository.MovimentacaoRepository;
import com.ciss.banco.service.ContaService;
import com.ciss.banco.service.MovimentacaoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovimentacaoServiceImpl implements MovimentacaoService {

    private final MovimentacaoRepository movimentacaoRepository;
    private final ContaService contaService;

    public MovimentacaoServiceImpl(MovimentacaoRepository movimentacaoRepository, ContaService contaService) {
        this.movimentacaoRepository = movimentacaoRepository;
        this.contaService = contaService;
    }

    @Override
    public List<Movimentacao> getAllByConta(Long contaId) {
        return movimentacaoRepository.findByContaOrigemIdOrContaDestinoId(contaId, contaId);
    }

    @Override
    public Movimentacao transferir(TransferenciaRequestDto transferenciaDto) throws Exception {
        Conta contaOrigem = contaService.getOne(transferenciaDto.getIdContaOrigem());
        if(contaOrigem.getSaldo().compareTo(transferenciaDto.getValor()) < 0 ){
            throw new Exception(String.format("Valor de transferência ultrapassa o saldo da conta de Origem. Saldo disponível: %s", contaOrigem.getSaldo().toString()));
        }
        Conta contaDestino = contaService.getOne(transferenciaDto.getIdContaDestino());

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setContaOrigem(contaOrigem);
        movimentacao.setContaDestino(contaDestino);
        movimentacao.setValor(transferenciaDto.getValor());
        movimentacao.setTipoMovimentacao(TipoMovimentacao.TRANSFERENCIA);
        movimentacao.setDebitoCredito(DebitoCredito.CREDITO);

        movimentacaoRepository.save(movimentacao);

        // Atualiza os Saldos das contas envolvidas
        contaOrigem.setSaldo( contaOrigem.getSaldo().subtract(movimentacao.getValor()));
        contaDestino.setSaldo( contaDestino.getSaldo().add(movimentacao.getValor()) );
        contaService.update(contaOrigem);
        contaService.update(contaDestino);

        return movimentacao;
    }

    @Override
    public Movimentacao depositar(DepositoRequestDto depositoDto) {
        Conta contaDestino = contaService.getOne(depositoDto.getIdContaDestino());

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setContaOrigem(null);
        movimentacao.setContaDestino(contaDestino);
        movimentacao.setValor(depositoDto.getValor());
        movimentacao.setTipoMovimentacao(TipoMovimentacao.DEPOSITO);
        movimentacao.setDebitoCredito(DebitoCredito.CREDITO);
        movimentacaoRepository.save(movimentacao);

        // Atualiza o Saldo da conta
        contaDestino.setSaldo( contaDestino.getSaldo().add(movimentacao.getValor()) );
        contaService.update(contaDestino);

        return movimentacao;
    }

    @Override
    public Movimentacao inicializaSaldo(Movimentacao movimentacao) {
        Movimentacao movimentacaoPersit = movimentacaoRepository.save(movimentacao);
        Conta conta = movimentacao.getContaDestino();
        conta.setSaldo(movimentacao.getValor());
        contaService.update(conta);
        return movimentacaoPersit;
    }

    @Override
    public Movimentacao sacar(SaqueRequestDto saqueDto) throws Exception {
        Conta contaOrigem = contaService.getOne(saqueDto.getIdContaOrigem());
        if(contaOrigem.getSaldo().compareTo(saqueDto.getValor()) < 0 ){
            throw new Exception(String.format("Valor de saque ultrapassa o saldo da conta de Origem. Saldo disponível: %s", contaOrigem.getSaldo().toString()));
        }

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setContaOrigem(contaOrigem);
        movimentacao.setContaDestino(null);
        movimentacao.setValor(saqueDto.getValor());
        movimentacao.setTipoMovimentacao(TipoMovimentacao.DEPOSITO);
        movimentacao.setDebitoCredito(DebitoCredito.DEBITO);

        movimentacaoRepository.save(movimentacao);

        // Atualiza o Saldo da conta
        contaOrigem.setSaldo( contaOrigem.getSaldo().subtract(movimentacao.getValor()) );
        contaService.update(contaOrigem);

        return movimentacao;
    }
}

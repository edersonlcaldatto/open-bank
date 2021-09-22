package com.ciss.banco.service.Impl;

import com.ciss.banco.dto.request.ContaRequestDto;
import com.ciss.banco.model.Cliente;
import com.ciss.banco.model.Conta;
import com.ciss.banco.model.Movimentacao;
import com.ciss.banco.model.enuns.DebitoCredito;
import com.ciss.banco.model.enuns.TipoMovimentacao;
import com.ciss.banco.repository.ContaRepository;
import com.ciss.banco.service.ClienteService;
import com.ciss.banco.service.ContaService;
import com.ciss.banco.service.MovimentacaoService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.util.List;

@Service
public class ContaServiceImpl implements ContaService {

    private final ContaRepository contaRepository;
    private final ClienteService clienteService;
    private final MovimentacaoService movimentacaoService;

    public ContaServiceImpl(ContaRepository contaRepository, ClienteService clienteService, @Lazy MovimentacaoService movimentacaoService) {
        this.contaRepository = contaRepository;
        this.clienteService = clienteService;
        this.movimentacaoService = movimentacaoService;
    }

    @Override
    public Conta create(Conta conta) {
        return contaRepository.save(conta);
    }

    @Override
    public Conta getOne(Long contaId) {
        return contaRepository.findById(contaId)
                .orElseThrow(() -> new NoResultException(String.format("Conta com código %d não encontrado", contaId)));
    }

    @Override
    public List<Conta> findAll() {
        return contaRepository.findAll();
    }

    @Override
    public void delete(Long contaId) {
        if (!contaRepository.existsById(contaId)){
            throw new NoResultException(String.format("Conta de código %d não localizado", contaId));
        }
        contaRepository.deleteById(contaId);
    }

    @Override
    public Conta update(Conta conta) {
        if (!contaRepository.existsById(conta.getId())){
            throw new NoResultException(String.format("Conta de código %d não localizado", conta.getId()));
        }
        return contaRepository.save(conta);
    }

    @Override
    public Conta addConta(ContaRequestDto contaRequestDto) {

        Cliente cliente = clienteService.getOne(contaRequestDto.getIdCliente());

        Conta contaPersit = new Conta();
        contaPersit.setNumeroConta(contaRequestDto.getNumeroConta());
        contaPersit.setAgencia(contaRequestDto.getAgencia());
        contaPersit.setCliente(cliente);
        contaRepository.save(contaPersit);

        List<Conta> contasCliente = cliente.getContas();
        contasCliente.add(contaPersit);
        clienteService.update(cliente);

        if(contaRequestDto.getSaldoInicial().compareTo(BigDecimal.ZERO) > 0){
            Movimentacao movimentacao = new Movimentacao();
            movimentacao.setContaOrigem(null);
            movimentacao.setContaDestino(contaPersit);
            movimentacao.setValor(contaRequestDto.getSaldoInicial());
            movimentacao.setTipoMovimentacao(TipoMovimentacao.SALDO_INIIAL);
            movimentacao.setDebitoCredito(DebitoCredito.CREDITO);
            movimentacaoService.inicializaSaldo(movimentacao);
        }
        return contaPersit;
    }
}

package com.ciss.banco.movimentacao;

import com.ciss.banco.dto.request.ContaRequestDto;
import com.ciss.banco.dto.request.DepositoRequestDto;
import com.ciss.banco.dto.request.SaqueRequestDto;
import com.ciss.banco.dto.request.TransferenciaRequestDto;
import com.ciss.banco.model.Cliente;
import com.ciss.banco.model.Conta;
import com.ciss.banco.model.Movimentacao;
import com.ciss.banco.repository.MovimentacaoRepository;
import com.ciss.banco.service.Impl.ClienteServiceImpl;
import com.ciss.banco.service.Impl.ContaServiceImpl;
import com.ciss.banco.service.Impl.MovimentacaoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class MovimentacaoServiceTest {

    @Autowired
    private MovimentacaoServiceImpl movimentacaoService;

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private ContaServiceImpl contaService;

    @Autowired
    private ClienteServiceImpl clienteService;


    @BeforeEach
    public void deleteAll() {
        this.movimentacaoRepository.deleteAll();
    }


    @Test
    @Transactional
    public void shouldInicializarSaldo() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setNome("Ederson");
        cliente.setDocumento("8888888888");
        cliente.setContas(new ArrayList<>());
        Cliente clientPersit = clienteService.create(cliente);

        ContaRequestDto contaDto = new ContaRequestDto();
        contaDto.setIdCliente(clientPersit.getId());
        contaDto.setNumeroConta("123456");
        contaDto.setAgencia("222");
        contaDto.setSaldoInicial(new BigDecimal("999.99"));
        Conta conta = contaService.addConta(contaDto);

        assertNotNull(conta.getId());
        assertEquals(conta.getSaldo(), contaDto.getSaldoInicial());
    }

    @Test
    @Transactional
    public void shouldTransferir() throws Exception {
        Cliente clienteOrigem = new Cliente();
        clienteOrigem.setNome("Ederson");
        clienteOrigem.setDocumento("8888888888");
        clienteOrigem.setContas(new ArrayList<>());
        Cliente clientPersitOrigem = clienteService.create(clienteOrigem);

        assertNotNull(clientPersitOrigem.getId());

        Cliente clienteDestino= new Cliente();
        clienteDestino.setNome("Diogo");
        clienteDestino.setDocumento("999999999");
        clienteDestino.setContas(new ArrayList<>());
        Cliente clientPersitDestino = clienteService.create(clienteDestino);

        assertNotNull(clientPersitDestino.getId());

        ContaRequestDto contaDtoOrigem = new ContaRequestDto();
        contaDtoOrigem.setIdCliente(clientPersitOrigem.getId());
        contaDtoOrigem.setNumeroConta("123456");
        contaDtoOrigem.setAgencia("222");
        contaDtoOrigem.setSaldoInicial(new BigDecimal("1000.00"));
        Conta contaOrigem = contaService.addConta(contaDtoOrigem);

        assertNotNull(contaOrigem.getId());

        ContaRequestDto contaDtoDestino = new ContaRequestDto();
        contaDtoDestino.setIdCliente(clientPersitDestino.getId());
        contaDtoDestino.setNumeroConta("123456");
        contaDtoDestino.setAgencia("222");
        contaDtoDestino.setSaldoInicial(new BigDecimal("500.00"));
        Conta contaDestino = contaService.addConta(contaDtoDestino);

        assertNotNull(contaDestino.getId());

        TransferenciaRequestDto transfDto = new TransferenciaRequestDto();
        transfDto.setIdContaOrigem(contaOrigem.getId());
        transfDto.setIdContaDestino(contaDestino.getId());
        transfDto.setValor(new BigDecimal("100.00"));
        Movimentacao movimentacao = movimentacaoService.transferir(transfDto);

        assertNotNull(movimentacao.getId());
    }

    @Test
    public void shouldSacar() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setNome("Diogo");
        cliente.setDocumento("999999999");
        cliente.setContas(new ArrayList<>());
        Cliente clientPersit = clienteService.create(cliente);

        assertNotNull(clientPersit.getId());

        ContaRequestDto contaDto = new ContaRequestDto();
        contaDto.setIdCliente(clientPersit.getId());
        contaDto.setNumeroConta("123456");
        contaDto.setAgencia("222");
        contaDto.setSaldoInicial(new BigDecimal("1000.00"));
        Conta conta = contaService.addConta(contaDto);

        SaqueRequestDto saqueDto = new SaqueRequestDto();
        saqueDto.setValor(new BigDecimal("100.00"));
        saqueDto.setIdContaOrigem(conta.getId());

        Movimentacao movimentacao = movimentacaoService.sacar(saqueDto);

        assertNotNull(movimentacao.getId());
        assertEquals(movimentacao.getContaOrigem().getSaldo(), new BigDecimal("900.00"));

    }

    @Test
    public void shouldDepositar() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setNome("Ederson");
        cliente.setDocumento("999999999");
        cliente.setContas(new ArrayList<>());
        Cliente clientPersit = clienteService.create(cliente);

        assertNotNull(clientPersit.getId());

        ContaRequestDto contaDto = new ContaRequestDto();
        contaDto.setIdCliente(clientPersit.getId());
        contaDto.setNumeroConta("123456");
        contaDto.setAgencia("222");
        contaDto.setSaldoInicial(new BigDecimal("1000.00"));
        Conta conta= contaService.addConta(contaDto);

        DepositoRequestDto depositoDto = new DepositoRequestDto();
        depositoDto.setValor(new BigDecimal("100.00"));
        depositoDto.setIdContaDestino(conta.getId());

        Movimentacao movimentacao = movimentacaoService.depositar(depositoDto);

        assertNotNull(movimentacao.getId());
        assertEquals(movimentacao.getContaDestino().getSaldo(), new BigDecimal("1100.00"));
    }

    @Test
    public void shouldFindAllByConta() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setNome("Ederson");
        cliente.setDocumento("999999999");
        cliente.setContas(new ArrayList<>());
        Cliente clientPersit = clienteService.create(cliente);

        assertNotNull(clientPersit.getId());

        ContaRequestDto contaDto = new ContaRequestDto();
        contaDto.setIdCliente(clientPersit.getId());
        contaDto.setNumeroConta("123456");
        contaDto.setAgencia("222");
        contaDto.setSaldoInicial(new BigDecimal("1000.00"));
        Conta conta= contaService.addConta(contaDto);

        DepositoRequestDto depositoDto = new DepositoRequestDto();
        depositoDto.setValor(new BigDecimal("100.00"));
        depositoDto.setIdContaDestino(conta.getId());

        Movimentacao movimentacao = movimentacaoService.depositar(depositoDto);

        assertNotNull(movimentacao.getId());

        List<Movimentacao> movimentacaoList = movimentacaoService.getAllByConta(conta.getId());

        assertEquals(2, movimentacaoList.size());
    }
}

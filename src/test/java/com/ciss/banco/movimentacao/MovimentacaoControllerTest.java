package com.ciss.banco.movimentacao;

import com.ciss.banco.dto.request.*;
import com.ciss.banco.model.Cliente;
import com.ciss.banco.model.Conta;
import com.ciss.banco.model.Movimentacao;
import com.ciss.banco.movimentacao.mock.ClienteToMovDtoMock;
import com.ciss.banco.movimentacao.mock.ContaToMovDtoMock;
import com.ciss.banco.repository.MovimentacaoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovimentacaoControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    private ClienteRequestDto clienteDto1;
    private ClienteRequestDto clienteDto2;
    private ContaRequestDto contaDto1;
    private ContaRequestDto contaDto2;

    @BeforeEach
    public void init(){
        this.contaDto1 = ContaToMovDtoMock.contaDto1();
        this.contaDto2 = ContaToMovDtoMock.contaDto2();
        this.clienteDto1 = ClienteToMovDtoMock.clienteDto1();
        this.clienteDto2 = ClienteToMovDtoMock.clienteDto2();
        movimentacaoRepository.deleteAll();
    }

    @AfterEach
    public void deleteAllAfter() {
        this.movimentacaoRepository.deleteAll();
    }

    @Test
    public void shouldDepositar(){

        ResponseEntity<Cliente> clienteEntitySave = this.restTemplate.postForEntity("http://localhost:" + port + "/cliente/",
                clienteDto1, Cliente.class);

        assertEquals(HttpStatus.CREATED, clienteEntitySave.getStatusCode());
        assertNotNull(clienteEntitySave.getBody());

        ResponseEntity<Conta> contaEntitySave = this.restTemplate.postForEntity("http://localhost:" + port + "/conta/add-conta",
                contaDto1, Conta.class);

        assertEquals(HttpStatus.CREATED, contaEntitySave.getStatusCode());
        assertNotNull(contaEntitySave.getBody());

        DepositoRequestDto depositoRequestDto = new DepositoRequestDto();
        depositoRequestDto.setValor(new BigDecimal(100));
        depositoRequestDto.setIdContaDestino(contaEntitySave.getBody().getId());

        ResponseEntity<Movimentacao> depositoEntitySave = this.restTemplate.postForEntity("http://localhost:" + port + "/movimentacao/depositar",
                depositoRequestDto, Movimentacao.class);

        assertEquals(HttpStatus.CREATED, depositoEntitySave.getStatusCode());
        assertNotNull(depositoEntitySave.getBody());
        assertNotNull(depositoEntitySave.getBody().getId());
        assertEquals(depositoRequestDto.getIdContaDestino(), depositoEntitySave.getBody().getContaDestino().getId());
        assertEquals(depositoRequestDto.getValor(), depositoEntitySave.getBody().getValor());
//      Verica o saldo da conta, com o valor depositado
        assertEquals(depositoEntitySave.getBody().getContaDestino().getSaldo(), contaEntitySave.getBody().getSaldo().add(new BigDecimal("100.00")));
    }

    @Test
    public void shouldSacar(){

        ResponseEntity<Cliente> clienteEntitySave = this.restTemplate.postForEntity("http://localhost:" + port + "/cliente/",
                clienteDto1, Cliente.class);

        assertEquals(HttpStatus.CREATED, clienteEntitySave.getStatusCode());
        assertNotNull(clienteEntitySave.getBody());

        ResponseEntity<Conta> contaEntitySave = this.restTemplate.postForEntity("http://localhost:" + port + "/conta/add-conta",
                contaDto1, Conta.class);

        assertEquals(HttpStatus.CREATED, contaEntitySave.getStatusCode());
        assertNotNull(contaEntitySave.getBody());

        SaqueRequestDto saqueRequestDto = new SaqueRequestDto();
        saqueRequestDto.setValor(new BigDecimal(100));
        saqueRequestDto.setIdContaOrigem(contaEntitySave.getBody().getId());

        ResponseEntity<Movimentacao> depositoEntitySave = this.restTemplate.postForEntity("http://localhost:" + port + "/movimentacao/sacar",
                saqueRequestDto, Movimentacao.class);

        assertEquals(HttpStatus.CREATED, depositoEntitySave.getStatusCode());
        assertNotNull(depositoEntitySave.getBody());
        assertNotNull(depositoEntitySave.getBody().getId());
        assertEquals(saqueRequestDto.getIdContaOrigem(), depositoEntitySave.getBody().getContaOrigem().getId());
        assertEquals(saqueRequestDto.getValor(), depositoEntitySave.getBody().getValor());
//      Verica o saldo da conta, com o valor sacado
        assertEquals(depositoEntitySave.getBody().getContaOrigem().getSaldo(), contaEntitySave.getBody().getSaldo().subtract(new BigDecimal("100.00")));
    }

    @Test
    public void shouldTransferir(){
        // Add cliente 1
        ResponseEntity<Cliente> cliente1EntitySave = this.restTemplate.postForEntity("http://localhost:" + port + "/cliente/",
                clienteDto1, Cliente.class);

        assertEquals(HttpStatus.CREATED, cliente1EntitySave.getStatusCode());
        assertNotNull(cliente1EntitySave.getBody());

        // Add cliente 2
        ResponseEntity<Cliente> cliente2EntitySave = this.restTemplate.postForEntity("http://localhost:" + port + "/cliente/",
                clienteDto2, Cliente.class);

        assertEquals(HttpStatus.CREATED, cliente2EntitySave.getStatusCode());
        assertNotNull(cliente2EntitySave.getBody());

        // cria uma conta1 para cada cliente1
        ResponseEntity<Conta> conta1EntitySave = this.restTemplate.postForEntity("http://localhost:" + port + "/conta/add-conta",
                contaDto1, Conta.class);

        assertEquals(HttpStatus.CREATED, conta1EntitySave.getStatusCode());
        assertNotNull(conta1EntitySave.getBody());

        // cria uma conta2 para cada cliente2
        ResponseEntity<Conta> conta2EntitySave = this.restTemplate.postForEntity("http://localhost:" + port + "/conta/add-conta",
                contaDto2, Conta.class);

        assertEquals(HttpStatus.CREATED, conta1EntitySave.getStatusCode());
        assertNotNull(conta1EntitySave.getBody());

        // Realiza a transferência
        TransferenciaRequestDto transferenciaDto = new TransferenciaRequestDto();
        transferenciaDto.setValor(new BigDecimal("500.00"));
        transferenciaDto.setIdContaOrigem(1L);
        transferenciaDto.setIdContaDestino(2L);

        ResponseEntity<Movimentacao> transferenciaEntitySave = this.restTemplate.postForEntity("http://localhost:" + port + "/movimentacao/transferir",
                transferenciaDto, Movimentacao.class);

        assertEquals(HttpStatus.CREATED, transferenciaEntitySave.getStatusCode());
        assertNotNull(transferenciaEntitySave.getBody());
        assertNotNull(transferenciaEntitySave.getBody().getId());
        assertEquals(transferenciaDto.getIdContaOrigem(), transferenciaEntitySave.getBody().getContaOrigem().getId());
        assertEquals(transferenciaDto.getIdContaDestino(), transferenciaEntitySave.getBody().getContaDestino().getId());
        assertEquals(transferenciaDto.getValor(), transferenciaEntitySave.getBody().getValor());
//      Verica o saldo da conta, com o valor transferido-debito
        assertEquals(transferenciaEntitySave.getBody().getContaOrigem().getSaldo(), conta1EntitySave.getBody().getSaldo().subtract(new BigDecimal("500.00")));
//      Verica o saldo da conta, com o valor transferido-credito
        assertEquals(transferenciaEntitySave.getBody().getContaDestino().getSaldo(), conta2EntitySave.getBody().getSaldo().add(new BigDecimal("500.00")));

    }

    public void shouldGetMovimentacaoConta(){

    }

//    @GetMapping("/{id-conta}")
//    ResponseEntity<List<Movimentacao>> getMovimentacaoByConta(@PathVariable("id-conta") Long contaId);
//
//    @PostMapping("/depositar")
//    @ResponseStatus(HttpStatus.CREATED)
//    ResponseEntity<Movimentacao> depositar(@Valid @RequestBody DepositoRequestDto depositoDto);
//
//    @Operation(summary = "Gera uma movimentação de Saque")
//    @PostMapping("/sacar")
//    @ResponseStatus(HttpStatus.CREATED)
//    ResponseEntity<Movimentacao> sacar(@Valid @RequestBody SaqueRequestDto saqueDto) throws Exception;
//
//    @Operation(summary = "Gera uma transferência entre as contas")
//    @PostMapping("/transferir")
//    @ResponseStatus(HttpStatus.CREATED)
//    ResponseEntity<Movimentacao> transferir(@Valid @RequestBody TransferenciaRequestDto transferenciaDto) throws Exception;

}

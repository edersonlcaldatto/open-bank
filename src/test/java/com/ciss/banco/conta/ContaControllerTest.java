package com.ciss.banco.conta;

import com.ciss.banco.cliente.mock.ClienteDtoMock;
import com.ciss.banco.conta.mock.ContaDtoMock;
import com.ciss.banco.dto.request.ClienteRequestDto;
import com.ciss.banco.dto.request.ContaRequestDto;
import com.ciss.banco.model.Cliente;
import com.ciss.banco.model.Conta;
import com.ciss.banco.repository.ClienteRepository;
import com.ciss.banco.repository.ContaRepository;
import com.ciss.banco.repository.MovimentacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContaControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ContaRepository contaRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;


    private ClienteRequestDto clienteDto;
    private ContaRequestDto contaDto;


    @BeforeEach
    public void init(){
        this.clienteDto = ClienteDtoMock.clienteDto1();
        this.contaDto = ContaDtoMock.getOne();
        clienteRepository.deleteAll();
        contaRepository.deleteAll();
        movimentacaoRepository.deleteAll();
    }

    @Test
    public void shloudFindNone(){
        ResponseEntity<Conta[]> responseEntity =
                this.restTemplate.getForEntity("http://localhost:" + port + "/conta/", Conta[].class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(0, responseEntity.getBody().length);
    }

    @Test
    public void shouldAddContaAndGetById(){
        ResponseEntity<Cliente> clienteEntitySave = this.restTemplate.postForEntity("http://localhost:" + port + "/cliente/",
                clienteDto, Cliente.class);

        assertEquals(HttpStatus.CREATED, clienteEntitySave.getStatusCode());
        assertNotNull(clienteEntitySave.getBody());

        ResponseEntity<Conta> responseEntitySave = this.restTemplate.postForEntity("http://localhost:" + port + "/conta/add-conta",
                contaDto, Conta.class);

        assertEquals(HttpStatus.CREATED, responseEntitySave.getStatusCode());
        assertNotNull(responseEntitySave.getBody());

        ResponseEntity<Conta> responseEntityFind =
                this.restTemplate.getForEntity("http://localhost:" + port + "/conta/" + responseEntitySave.getBody().getId(),
                        Conta.class);

        assertEquals(HttpStatus.OK, responseEntityFind.getStatusCode());
        assertNotNull(responseEntityFind.getBody());
        assertNotNull(responseEntityFind.getBody().getId());
        assertEquals(responseEntitySave.getBody().getNumeroConta(), responseEntityFind.getBody().getNumeroConta());
        assertEquals(responseEntitySave.getBody().getAgencia(), responseEntityFind.getBody().getAgencia());
        assertEquals(responseEntitySave.getBody().getCliente().getNome(), responseEntityFind.getBody().getCliente().getNome());
        assertEquals(responseEntitySave.getBody().getCliente().getDocumento(), responseEntityFind.getBody().getCliente().getDocumento());

    }

}

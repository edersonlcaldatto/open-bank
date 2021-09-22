package com.ciss.banco.cliente;

import com.ciss.banco.cliente.mock.ClienteDtoMock;
import com.ciss.banco.cliente.mock.ClienteMock;
import com.ciss.banco.dto.request.ClienteRequestDto;
import com.ciss.banco.model.Cliente;
import com.ciss.banco.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClienteControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    ClienteRepository clienteRepository;

    private Cliente cliente1;

    private ClienteRequestDto clienteDto1;

    @BeforeEach
    public void deleteAll(){
        this.cliente1 = ClienteMock.cliente1();
        this.clienteDto1 = ClienteDtoMock.clienteDto1();
        this.clienteRepository.deleteAll();
    }

    @Test
    public void shloudFindNone(){
        ResponseEntity<Cliente[]> responseEntity =
                this.restTemplate.getForEntity("http://localhost:" + port + "/cliente/", Cliente[].class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(0, responseEntity.getBody().length);
    }

    @Test
    public void shouldFindIdCliente(){
        ResponseEntity<Cliente> responseEntitySave = this.restTemplate.postForEntity("http://localhost:" + port + "/cliente/",
                clienteDto1, Cliente.class);

        assertEquals(HttpStatus.CREATED, responseEntitySave.getStatusCode());
        assertNotNull(responseEntitySave.getBody());

        ResponseEntity<Cliente> responseEntityFind =
                this.restTemplate.getForEntity("http://localhost:" + port + "/cliente/" + responseEntitySave.getBody().getId(),
                        Cliente.class);

        assertEquals(HttpStatus.OK, responseEntityFind.getStatusCode());
        assertNotNull(responseEntityFind.getBody());
        assertNotNull(responseEntityFind.getBody().getId());
        assertEquals(responseEntitySave.getBody().getNome(), responseEntityFind.getBody().getNome());
        assertEquals(responseEntitySave.getBody().getDocumento(), responseEntityFind.getBody().getDocumento());
    }

    @Test
    public void shouldUpdateCliente() {
        ResponseEntity<Cliente> responseEntitySave = this.restTemplate.postForEntity("http://localhost:" + port + "/cliente/",
                clienteDto1, Cliente.class);
        assertEquals(HttpStatus.CREATED, responseEntitySave.getStatusCode());
        assertNotNull(responseEntitySave.getBody());

        clienteDto1.setNome("Cliente um");

        HttpEntity<ClienteRequestDto> httpEntity = new HttpEntity<>(clienteDto1);
        ResponseEntity<Cliente> responseEntityUpdate =
                this.restTemplate.exchange("http://localhost:" + port + "/cliente/" + responseEntitySave.getBody().getId(),
                        HttpMethod.PUT, httpEntity, Cliente.class);

        assertEquals(HttpStatus.OK, responseEntityUpdate.getStatusCode());
        assertNotNull(responseEntityUpdate.getBody());
        assertNotNull(responseEntityUpdate.getBody().getId());
        assertEquals(responseEntityUpdate.getBody().getNome(), "Cliente um");
    }

    @Test
    public void shouldDeleteCliente(){
        ResponseEntity<Cliente> responseEntitySave = this.restTemplate.postForEntity("http://localhost:" + port + "/cliente/",
                clienteDto1, Cliente.class);

        assertEquals(HttpStatus.CREATED, responseEntitySave.getStatusCode());
        assertNotNull(responseEntitySave.getBody());

        this.restTemplate.delete("http://localhost:" + port + "/cliente/" + responseEntitySave.getBody().getId());

        ResponseEntity<Cliente> responseEntityFind =
                this.restTemplate.getForEntity("http://localhost:" + port + "/cliente/" + responseEntitySave.getBody().getId(), Cliente.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntityFind.getStatusCode());
    }

}

package com.ciss.banco.cliente;

import com.ciss.banco.cliente.mock.ClienteMock;
import com.ciss.banco.model.Cliente;
import com.ciss.banco.repository.ClienteRepository;
import com.ciss.banco.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ClienteServiceTest {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteRepository clienteRepository;

    private Cliente cliente;

    @BeforeEach
    public void setUp(){
        this.cliente = ClienteMock.cliente1();
        clienteRepository.deleteAll();
    }

    @Test
    public void shouldCreate() throws Exception {
        this.cliente = clienteService.create(cliente);

        assertNotNull(this.cliente.getId());
    }

    @Test
    public void shouldGetOne() throws Exception {
        this.cliente = clienteService.create(cliente);
        assertNotNull(this.cliente.getId());

        Cliente clienteFind = clienteService.getOne(cliente.getId());
        assertNotNull(clienteFind);
    }

    @Test
    public void shouldFindAll() throws Exception {
        for (int i = 0; i < 10; i++) {
            Cliente cliente = ClienteMock.cliente1();
            cliente.setDocumento(cliente.getDocumento()+i);
            clienteService.create(cliente);
        }

        List<Cliente> all = clienteService.findAll();
        assertEquals(10, all.size());
    }

    @Test
    public void ShouldVerifyDocumentExists() throws Exception {
        clienteService.create(cliente);
        Exception exception = assertThrows(Exception.class, () -> clienteService.create(cliente));
        assertEquals("Cliente ja cadastrado com o documento 123456789", exception.getMessage());
    }

    @Test
    public void shouldDeleteAddressById() throws Exception {
        this.cliente = clienteService.create(cliente);
        clienteService.deleteById(cliente.getId());

        List<Cliente> all = clienteService.findAll();
        assertEquals(0, all.size());
    }

    @Test
    public void shouldUpdateAddress() throws Exception {
        this.cliente = clienteService.create(cliente);
        this.cliente.setNome("Nome Diferente");

        Cliente byId = clienteService.update(cliente);
        assertNotNull(byId);
        assertEquals("Nome Diferente", cliente.getNome());
    }

}

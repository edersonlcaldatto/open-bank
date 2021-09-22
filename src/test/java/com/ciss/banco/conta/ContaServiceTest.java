package com.ciss.banco.conta;

import com.ciss.banco.cliente.mock.ClienteMock;
import com.ciss.banco.dto.request.ContaRequestDto;
import com.ciss.banco.model.Cliente;
import com.ciss.banco.model.Conta;
import com.ciss.banco.repository.ContaRepository;
import com.ciss.banco.service.ClienteService;
import com.ciss.banco.service.ContaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ContaServiceTest {

    @Autowired
    private ContaService contaService;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private ClienteService clienteService;

    @BeforeEach
    public void setUp(){
        contaRepository.deleteAll();
    }

    @Test
    @Transactional
    public void shouldAddConta() throws Exception {
        Cliente cliente = ClienteMock.cliente1();
        clienteService.create(cliente);

        assertNotNull(cliente.getId());

        ContaRequestDto contaDto = new ContaRequestDto();
        contaDto.setNumeroConta("123456");
        contaDto.setAgencia("123");
        contaDto.setIdCliente(cliente.getId());
        contaDto.setSaldoInicial(new BigDecimal("150.00"));
        Conta conta = contaService.addConta(contaDto);

        assertNotNull(conta.getId());
        assertEquals(conta.getSaldo(), new BigDecimal("150.00"));
    }


}

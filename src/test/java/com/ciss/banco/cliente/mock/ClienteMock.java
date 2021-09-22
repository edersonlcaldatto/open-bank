package com.ciss.banco.cliente.mock;

import com.ciss.banco.model.Cliente;

import java.util.ArrayList;

public class ClienteMock {

    public static Cliente cliente1(){
        Cliente cliente = new Cliente();
        cliente.setNome("Cliente 1");
        cliente.setDocumento("123456789");
        cliente.setContas(new ArrayList<>());
        return cliente;
    }

    public static Cliente cliente2(){
        Cliente cliente = new Cliente();
        cliente.setNome("Cliente 2");
        cliente.setDocumento("987654321");
        cliente.setContas(new ArrayList<>());
        return cliente;
    }
}

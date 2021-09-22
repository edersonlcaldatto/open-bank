package com.ciss.banco.service;

import com.ciss.banco.dto.request.ContaRequestDto;
import com.ciss.banco.model.Conta;

import java.util.List;

public interface ContaService {

    Conta create(Conta conta);

    Conta getOne(Long contaId);

    List<Conta> findAll();

    void delete(Long contaId);

    Conta update(Conta conta);

    Conta addConta(ContaRequestDto contaRequestDto);
}

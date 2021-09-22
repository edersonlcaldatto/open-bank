package com.ciss.banco.repository;

import com.ciss.banco.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContaRepository extends JpaRepository<Conta, Long> {

    Optional<Conta> getContaByNumeroContaAndAgencia(String conta, String agencia);


}

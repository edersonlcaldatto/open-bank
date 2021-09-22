package com.ciss.banco.repository;

import com.ciss.banco.model.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {

    List<Movimentacao> findByContaOrigemIdOrContaDestinoId(Long contaOrigemId, Long contaDestivoId);


}

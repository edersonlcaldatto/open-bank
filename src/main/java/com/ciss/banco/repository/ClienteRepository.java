package com.ciss.banco.repository;

import com.ciss.banco.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Boolean existsByDocumento(String documento);


}

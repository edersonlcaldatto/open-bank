package com.ciss.banco.api.Impl;

import com.ciss.banco.api.ContaController;
import com.ciss.banco.dto.request.ContaRequestDto;
import com.ciss.banco.model.Conta;
import com.ciss.banco.service.ContaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/conta")
public class ContaControllerImpl implements ContaController {

    private final ContaService contaService;

    public ContaControllerImpl(ContaService contaService) {
        this.contaService = contaService;
    }

    @Override
    public ResponseEntity<List<Conta>> getAll() {
        return ResponseEntity.ok(contaService.findAll());
    }

    @Override
    public ResponseEntity<Conta> getById(Long contaId) {
        return ResponseEntity.ok(contaService.getOne(contaId));
    }

    @Override
    public ResponseEntity<Conta> addConta(@Valid ContaRequestDto contaRequestDto) {
        Conta toReturn = contaService.addConta(contaRequestDto);
        return ResponseEntity.created(URI.create("/conta/" + toReturn.getId())).body(toReturn);
    }
}

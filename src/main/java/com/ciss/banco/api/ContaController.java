package com.ciss.banco.api;

import com.ciss.banco.dto.request.ContaRequestDto;
import com.ciss.banco.model.Conta;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

public interface ContaController {

    @Operation(summary = "Retorna a lista de Contas cadastradas.")
    @GetMapping()
    ResponseEntity<List<Conta>> getAll();

    @Operation(summary = "Retorna a conta com base no parametro informado.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(
                            mediaType = "aplication/json",
                            examples = @ExampleObject(
                                    value = "codigo: X_100"
                            )
                    )
            )
    })
    @GetMapping("/{id}")
    ResponseEntity<Conta> getById(@PathVariable("id") Long contaId);

    @Operation(summary = "Insere uma nova conta para o cliente")
    @PostMapping("/add-conta")
    ResponseEntity<Conta> addConta(@Valid @RequestBody ContaRequestDto contaRequestDto);

}

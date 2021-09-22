package com.ciss.banco.api;

import com.ciss.banco.dto.request.ClienteRequestDto;
import com.ciss.banco.model.Cliente;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface ClienteController {

    @Operation(summary = "Retorna a lista de Clientes cadastrados.")
    @GetMapping()
    ResponseEntity<List<Cliente>> getAll();

    @Operation(summary = "Retorna o Cliente com base no parametro informado.")
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
    ResponseEntity<Cliente> getById(@PathVariable("id") Long clienteId);

    @Operation(summary = "Insere um novo Cliente")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<Cliente> create(@Valid @RequestBody ClienteRequestDto cliente) throws Exception;

    @Operation(summary = "Atualiza o cliente conforme o parametro informado")
    @PutMapping("/{id}")
    ResponseEntity<Cliente> update(@PathVariable(value = "id") long clienteId,
                   @RequestBody ClienteRequestDto clienteDto);

    @Operation(summary = "Excluí o cliente conforme parametro informado")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteById(@PathVariable(value = "id") Long clienteId);

}

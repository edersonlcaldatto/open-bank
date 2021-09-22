package com.ciss.banco.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class ClienteRequestDto {

    @NotEmpty(message = "Nome é obrigatorio")
    private String nome;
    @NotEmpty(message = "Documento é obrigatório")
    private String documento;

}

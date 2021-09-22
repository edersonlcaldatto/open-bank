package com.ciss.banco.dto.response;

import com.ciss.banco.model.Conta;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ClienteResponseDto {

    private Long id;
    private String documento;
    private String nome;
    private List<Conta> contas;


}

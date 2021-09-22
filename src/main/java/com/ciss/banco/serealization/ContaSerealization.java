package com.ciss.banco.serealization;

import com.ciss.banco.dto.response.ContaResponseDto;
import com.ciss.banco.model.Conta;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.modelmapper.ModelMapper;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class ContaSerealization extends JsonSerializer<Conta> {

    private final ModelMapper modelMapper;

    public ContaSerealization(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public void serialize(Conta conta, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        ContaResponseDto contaResponseDto = modelMapper.map(conta , ContaResponseDto.class);
        jsonGenerator.writeObject(contaResponseDto);

    }
}

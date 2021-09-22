package com.ciss.banco.serealization;

import com.ciss.banco.dto.response.MovimentacaoResponseDto;
import com.ciss.banco.model.Movimentacao;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.modelmapper.ModelMapper;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class MovimentacaoSerealization extends JsonSerializer<Movimentacao> {

    private final ModelMapper modelMapper;

    public MovimentacaoSerealization(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public void serialize(Movimentacao movimentacao, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        MovimentacaoResponseDto movimentacaoResponseDto = modelMapper.map(movimentacao , MovimentacaoResponseDto.class);
        jsonGenerator.writeObject(movimentacaoResponseDto);
    }
}

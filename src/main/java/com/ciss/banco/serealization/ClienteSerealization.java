package com.ciss.banco.serealization;

import com.ciss.banco.dto.response.ClienteResponseDto;
import com.ciss.banco.model.Cliente;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.modelmapper.ModelMapper;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class ClienteSerealization extends JsonSerializer<Cliente> {

    private final ModelMapper modelMapper;

    public ClienteSerealization(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public void serialize(Cliente cliente, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        ClienteResponseDto clienteResponseDto = modelMapper.map(cliente , ClienteResponseDto.class);
        jsonGenerator.writeObject(clienteResponseDto);

    }
}

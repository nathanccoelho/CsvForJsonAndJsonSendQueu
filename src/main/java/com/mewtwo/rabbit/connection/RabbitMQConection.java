package com.mewtwo.rabbit.connection;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mewtwo.rabbit.constantes.JsonConstante;
import com.mewtwo.rabbit.model.JsonDto;
import com.mewtwo.rabbit.service.Conversor;
import com.mewtwo.rabbit.service.RabbitMqService;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class RabbitMQConection {

    private static final String NOME_EXCHANGE = "amq.direct";

    private final AmqpAdmin amqpAdmin;
    private final ObjectMapper objectMapper;
    private final RabbitMqService rabbitMqService;

    @Autowired
    public RabbitMQConection(AmqpAdmin amqpAdmin, ObjectMapper objectMapper, RabbitMqService rabbitMqService) {
        this.amqpAdmin = amqpAdmin;
        this.objectMapper = objectMapper;
        this.rabbitMqService = rabbitMqService;
    }

    @PostConstruct
    public void inicializar() {
        Queue filaJson = fila(JsonConstante.FILA_JSON);
        DirectExchange troca = trocaDireta();
        Binding ligacaoJson = relacionamento(filaJson, troca);

        amqpAdmin.declareExchange(troca);
        amqpAdmin.declareQueue(filaJson);
        amqpAdmin.declareBinding(ligacaoJson);
        Conversor conversor = new Conversor();
        conversor.leitor();

        enviarObjetosParaFila();
    }

    public void enviarObjetosParaFila() {
        try {
            List<JsonDto> jsonDtoList = readJsonFromFile("output.json");
            for (JsonDto jsonDto : jsonDtoList) {
                // Converter JsonDto para JSON e enviar para a fila
            	System.out.println("Enviando objetos Json para a fila");
                String jsonStr = objectMapper.writeValueAsString(jsonDto);
                rabbitMqService.enviaMensagem(JsonConstante.FILA_JSON, jsonStr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Queue fila(String nomeFila) {
        return new Queue(nomeFila, true, false, false);
    }

    private DirectExchange trocaDireta() {
        return new DirectExchange(NOME_EXCHANGE);
    }

    private Binding relacionamento(Queue fila, DirectExchange troca) {
        return new Binding(fila.getName(), Binding.DestinationType.QUEUE, troca.getName(), fila.getName(), null);
    }

    private List<JsonDto> readJsonFromFile(String filePath) throws IOException {
        return objectMapper.readValue(new File(filePath), new TypeReference<List<JsonDto>>() {});
    }
}
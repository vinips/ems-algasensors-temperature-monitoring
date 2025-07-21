package com.algaworks.algasensors.temperature.monitoring.infrastructure.rabbitmq;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMQInitializer {

    private final RabbitAdmin rabbitAdmin;

    //Apos o rabbitAdmin ser injetado, vai chamar esse metodo e as configuracoes
    //Feitas na classe RabbitMQConfig vao ser criadas.
    @PostConstruct
    public void init(){
        rabbitAdmin.initialize();
    }
}

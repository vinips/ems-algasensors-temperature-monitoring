package com.algaworks.algasensors.temperature.monitoring.infrastructure.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String FANOUT_EXCHANGE_PROCESS_TEMPERATURE = "temperature-processing.temperature.received.v1.e";
    public static final String QUEUE_PROCESS_TEMPERATURE = "temperature-monitoring.process-temperature.v1.q";
    public static final String QUEUE_SENSOR_ALERT = "temperature-monitoring.sensor-alert.v1.q";

    //Esse Bean é necessario para converter um Objeto complexo (TemperatureLogData por exemplo)
    //Sem esse bean ele nao consegue converter e da erro na hora de enviar para fila.
    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public Queue queueProcessTemperature() {
        return QueueBuilder
                .durable(QUEUE_PROCESS_TEMPERATURE)
                .build();
    }

    @Bean
    public Queue queueSensorAlert() {
        return QueueBuilder
                .durable(QUEUE_SENSOR_ALERT)
                .build();
    }

    //Aqui ele nao vai ser um bean. Declaramos ele para realizar o Binding entre a Exchange e a Queue.
    //Quem cria esse Bean da Exchange é o producer dela, o temperature-processing.
    public FanoutExchange exchange() {
        return ExchangeBuilder
                .fanoutExchange(FANOUT_EXCHANGE_PROCESS_TEMPERATURE)
                .build();
    }

    @Bean
    public Binding bindingProcessTemperature(){
        return BindingBuilder.bind(queueProcessTemperature()).to(exchange());
    }

    @Bean
    public Binding bindingSensorAlert(){
        return BindingBuilder.bind(queueSensorAlert()).to(exchange());
    }


}

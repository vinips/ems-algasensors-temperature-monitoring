package com.algaworks.algasensors.temperature.monitoring.infrastructure.rabbitmq;

import com.algaworks.algasensors.temperature.monitoring.api.model.TemperatureLogData;
import com.algaworks.algasensors.temperature.monitoring.domain.service.TemperatureMonitoringService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMQListener {

    private final TemperatureMonitoringService temperatureMonitoringService;

    @SneakyThrows
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void handle(@Payload TemperatureLogData temperatureLogData) {
        temperatureMonitoringService.processTemperatureReading(temperatureLogData);
        Thread.sleep(Duration.ofSeconds(5));
    }


}

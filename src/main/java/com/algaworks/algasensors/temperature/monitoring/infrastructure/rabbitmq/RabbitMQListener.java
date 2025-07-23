package com.algaworks.algasensors.temperature.monitoring.infrastructure.rabbitmq;

import com.algaworks.algasensors.temperature.monitoring.api.model.TemperatureLogData;
import com.algaworks.algasensors.temperature.monitoring.domain.service.SensorAlertService;
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
    private final SensorAlertService sensorAlertService;

    //O parametro concurrency(x-y) diz o valor minimo (x) e maximo (y)
    // de Threads que esse Listener pode ter.
    @SneakyThrows
    @RabbitListener(queues = RabbitMQConfig.QUEUE_PROCESS_TEMPERATURE, concurrency = "2-3")
    public void handleProcessTemperature(@Payload TemperatureLogData temperatureLogData) {
        temperatureMonitoringService.processTemperatureReading(temperatureLogData);
        Thread.sleep(Duration.ofSeconds(5));
    }

    @SneakyThrows
    @RabbitListener(queues = RabbitMQConfig.QUEUE_SENSOR_ALERT, concurrency = "2-3")
    public void handleSensorAlert(@Payload TemperatureLogData temperatureLogData) {
        sensorAlertService.sensorAlertReading(temperatureLogData);
        Thread.sleep(Duration.ofSeconds(5));
    }


}

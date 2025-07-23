package com.algaworks.algasensors.temperature.monitoring.domain.service;

import com.algaworks.algasensors.temperature.monitoring.api.model.TemperatureLogData;
import com.algaworks.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.algaworks.algasensors.temperature.monitoring.domain.model.SensorId;
import com.algaworks.algasensors.temperature.monitoring.domain.repository.SensorAlertRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SensorAlertService {

    private final SensorAlertRepository sensorAlertRepository;

    @Transactional
    public void sensorAlertReading(TemperatureLogData temperatureLogData) {
        sensorAlertRepository.findById(new SensorId(temperatureLogData.getSensorId()))
                .ifPresentOrElse(
                        alert -> handleAlert(temperatureLogData, alert),
                        () -> logIgnoredAlert(temperatureLogData)
                );
    }

    private void handleAlert(TemperatureLogData temperatureLogData, SensorAlert sensorAlert) {
        if (sensorAlert.getMaxTemperature() != null
                && temperatureLogData.getTemperature().compareTo(sensorAlert.getMaxTemperature()) >= 0) {

            log.info("Alert Max Temp: SensorId {} Temp {}",
                    temperatureLogData.getSensorId(), temperatureLogData.getTemperature());
        } else if (sensorAlert.getMinTemperature() != null
                && temperatureLogData.getTemperature().compareTo(sensorAlert.getMinTemperature()) <= 0) {

            log.info("Alert Min Temp: SensorId {} Temp {}",
                    temperatureLogData.getSensorId(), temperatureLogData.getTemperature());
        } else {
            logIgnoredAlert(temperatureLogData);
        }
    }

    private void logIgnoredAlert(TemperatureLogData temperatureLogData) {
        log.info("Alert Ignored: SensorId {} Temp {}",
                temperatureLogData.getSensorId(), temperatureLogData.getTemperature());
    }
}

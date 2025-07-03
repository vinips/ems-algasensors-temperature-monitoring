package com.algaworks.algasensors.temperature.monitoring.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TemperatureLog {

    @Id
    @AttributeOverride(name = "value", column = @Column(name= "id", columnDefinition = "uuid"))
    private TemperatureLogId id;

    private Double temperature;

    private OffsetDateTime registeredAt;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "sensor_id", columnDefinition = "BIGINT"))
    private SensorId sensorId;

}

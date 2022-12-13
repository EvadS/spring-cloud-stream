package com.se.sample;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherRest implements KafkaTimeMessage {

    @NonNull
    private Long deviceId;

    @NonNull @JsonFormat(shape= JsonFormat.Shape.STRING )
    private Instant timestamp;

    private Float temperatureCelsius;

    private float humidity;

    @Override
    public Long getEpochMillis(){
        return timestamp.toEpochMilli();
    }
}

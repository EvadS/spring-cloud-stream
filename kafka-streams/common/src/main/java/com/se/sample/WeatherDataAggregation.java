package com.se.sample;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherDataAggregation {

    private Long key;
    private Instant endTime;
    private double avgTempCelsius;
    private double avgHumidity;
}

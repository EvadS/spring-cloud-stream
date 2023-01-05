package com.example.streams;

import com.se.sample.WeatherDataAggregation;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
@Slf4j
public class WeatherAlertsProcessor {

    public static final double TEMPERATURE_ALARM_THRESHOLD_CELSIUS = 33.0;
    public static final double HUMIDITY_ALARM_THRESHOLD_PERCENTS = 50.0;


    //TODO: HERE
    @Bean
    public Function<KStream<Long, WeatherDataAggregation>, KStream<Long, WeatherDataAggregation>> alarmProcessor() {
        return input -> input.filter(this::thresholdReached);
    }

    private boolean thresholdReached(Long deviceId, WeatherDataAggregation weatherDataAggregation) {

        var measuredTemperature = weatherDataAggregation.getAvgTempCelsius();
        var measureHumidity = weatherDataAggregation.getAvgHumidity();

        var isTempThresholdReached = measuredTemperature > TEMPERATURE_ALARM_THRESHOLD_CELSIUS;
        var isHumThresholdReached = measureHumidity > HUMIDITY_ALARM_THRESHOLD_PERCENTS;

        if (isTempThresholdReached) {
            log.warn("Device # {}: Temperature [{}] is way above the normal value [{}]", deviceId, measuredTemperature, TEMPERATURE_ALARM_THRESHOLD_CELSIUS);
        }
        if (isHumThresholdReached) {
            log.warn("Device # {}: Humidity [{}] is way above the normal value [{}]", deviceId, measuredTemperature, HUMIDITY_ALARM_THRESHOLD_PERCENTS);

        }
        return isTempThresholdReached || isHumThresholdReached;

    }
}

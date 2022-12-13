package com.example.streams;


import com.se.sample.WeatherData;
import com.se.sample.WeatherDataAggregation;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.time.Duration;
import java.util.function.Function;

@Configuration
@Slf4j
public class WeatherDataAggregator {

    @Bean
    public Function<KStream<Long, WeatherData>, KStream<Long, WeatherDataAggregation>> aggregateWeather() {
        return input -> input
                .peek((key, value) -> log.info("enrich: Received key [{}], with value [{}]", key, value))
                .groupByKey()
                .windowedBy(TimeWindows.of(Duration.ofSeconds(5)).grace(Duration.ZERO))
                .aggregate(this::init, this::agg,
                         Materialized.with(Serdes.Long(),
                        new JsonSerde<>(IntermediateAggState.class)) )
                .suppress(Suppressed.untilWindowCloses(Suppressed.BufferConfig.unbounded()))
                .toStream()
                .map(this::calcAvg);
    }

    private KeyValue<Long, WeatherDataAggregation> calcAvg(Windowed<Long> longWindowed, IntermediateAggState intermediateAggState) {

        double averageTemperature = intermediateAggState.getTempSum()/ intermediateAggState.getTempCount();
        double averageHumidity = intermediateAggState.getHumSum() / intermediateAggState.getHumCount();


        var statistics = new WeatherDataAggregation(longWindowed.key(),
                longWindowed.window().endTime(),averageTemperature, averageHumidity);

        return KeyValue.pair(longWindowed.key(), statistics);
    }

    private IntermediateAggState agg(Long deviceId, WeatherData weatherData,
                                     IntermediateAggState aggregation) {

        log.debug("Aggregating incoming message[{}]", weatherData);

        int tempCount = aggregation.getTempCount();
        double tempSum = aggregation.getTempSum();
        int humCount = aggregation.getHumCount();
        double humSum = aggregation.getHumSum();

        tempCount++;
        tempSum += weatherData.getTemperatureCelsius().doubleValue();

        humCount++;
        humSum += weatherData.getHumidity().doubleValue();

        return new IntermediateAggState(tempCount, tempSum, humCount, humSum);


    }

    private IntermediateAggState init() {
        return new IntermediateAggState(0, 0d, 0, 0d);
    }

}

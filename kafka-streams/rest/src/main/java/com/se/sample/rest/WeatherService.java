package com.se.sample.rest;

import com.se.sample.WeatherData;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@Slf4j
public class WeatherService {
    public static final String DEMO_JAVA_TOPIC_NAME = "weather-data";
    public static final String bootstrapServers = "127.0.0.1:9092";

    public void postWeatherData(WeatherData weatherData) {
        log.debug("Handle weather data: {}", weatherData);


    }


}

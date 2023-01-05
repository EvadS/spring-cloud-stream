package com.se.sample.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se.sample.CustomerDeserializer;
import com.se.sample.WeatherData;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@Slf4j
public class WeatherService {
    public static final String DEMO_JAVA_TOPIC_NAME = "weather-data";
    public static final String bootstrapServers = "127.0.0.1:9092";

    public void postWeatherData(WeatherData weatherData) throws JsonProcessingException {
        log.debug("Handle weather data: {}", weatherData);

        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonNode  = objectMapper.writeValueAsString(weatherData);
    //    String value = jsonNode.asText();

        //-------------------------------
        ProducerRecord<String, String> producerRecord =
                new ProducerRecord<String, String>(DEMO_JAVA_TOPIC_NAME,jsonNode);

        // send data - asynchronous
        producer.send(producerRecord, new Callback() {
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                // executes every time a record is successfully sent or an exception is thrown
                if (e == null) {
                    // the record was successfully sent
                    log.info("Received new metadata. \n" +
                            "Topic:" + recordMetadata.topic() + "\n" +
                            "Partition: " + recordMetadata.partition() + "\n" +
                            "Offset: " + recordMetadata.offset() + "\n" +
                            "Timestamp: " + recordMetadata.timestamp());
                } else {
                    log.error("Error while producing", e);
                }
            }
        });

        // create the producer


    }


}

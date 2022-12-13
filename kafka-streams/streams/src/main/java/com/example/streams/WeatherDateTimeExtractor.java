package com.example.streams;

import com.se.sample.KafkaTimeMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

public class WeatherDateTimeExtractor implements TimestampExtractor {
    @Override
    public long extract(ConsumerRecord<Object, Object> consumerRecord, long partitionTime) {
        KafkaTimeMessage value = ((KafkaTimeMessage) consumerRecord.value());
        return value.getEpochMillis();
    }
}

package com.barrage.api.dto;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;

import java.io.Serializable;

/**
 * @Author TomShiDi
 * @Description
 * @Date 2020/4/9
 **/
public class SendResultDto<K, V> extends SendResult<K, V> implements Serializable {
    public SendResultDto(ProducerRecord<K, V> producerRecord, RecordMetadata recordMetadata) {
        super(producerRecord, recordMetadata);
    }
}

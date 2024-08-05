package com.kafka.rabbitmq.redis.service;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
public class kafkaProducer {

	private final static Logger LOGGER = LoggerFactory.getLogger(kafkaProducer.class);

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void sendMessage(String topic, String message) {
		try {
			CompletableFuture<SendResult<String, String>> send = kafkaTemplate.send(topic, message);
			LOGGER.info("MESSAGE SEND SUCCESSFULLY");

		} catch (Exception e) {
			LOGGER.error("MESSAGE  NOT SEND SUCCESSFULLY  {}", e.getMessage());
		}

	}

}

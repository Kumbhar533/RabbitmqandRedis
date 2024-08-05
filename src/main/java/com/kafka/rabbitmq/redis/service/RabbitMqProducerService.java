package com.kafka.rabbitmq.redis.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqProducerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMqProducerService.class);

	@Autowired
	private AmqpTemplate amqpTemplate;

	public String sendMessages(String routingKey, String message) {

		try {
			amqpTemplate.convertAndSend("my-exchange", routingKey, message);

			LOGGER.info("Sent message with routing key '" + routingKey + "': " + message);
			return "messages are published successfuly";
		} catch (Exception e) {
			return e.getMessage();
		}

	}

}

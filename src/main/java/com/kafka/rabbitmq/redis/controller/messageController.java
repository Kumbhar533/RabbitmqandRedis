package com.kafka.rabbitmq.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kafka.rabbitmq.redis.service.KafkaConsumer;
import com.kafka.rabbitmq.redis.service.RabbitMqConsumerService;
import com.kafka.rabbitmq.redis.service.RabbitMqProducerService;
import com.kafka.rabbitmq.redis.service.kafkaProducer;

@RestController
public class messageController {

	@Autowired
	private kafkaProducer kafkaProducer;

	@Autowired
	private RabbitMqProducerService rabbitMqProducerService;

	@Autowired
	private KafkaConsumer kafkaConsumer;

	@Autowired
	private RabbitMqConsumerService rabbitMqConsumerService;

	@PostMapping("/send")
	public String sendMessageViaKafka(@RequestBody String message) {

		kafkaProducer.sendMessage("my-topic", message);

		return "message published successfully";
	}

	@PostMapping("/send/message")
	public String sendMessageViaRabblitMq(@RequestBody String message) {

		rabbitMqProducerService.sendMessages("routing.key.specific", message);

		return "message published successfully";
	}

	@GetMapping("/{key}")
	public String getMessage(@PathVariable String key) {

		try {
			String messageFromRedis = kafkaConsumer.getMessageFromRedis(key);

			System.out.println("message of kafka " + " ---> " + messageFromRedis);

			String messageFromRedis2 = rabbitMqConsumerService.getMessageFromRedis(key);

			System.out.println("message of rabbitmq " + " ---> " + messageFromRedis2);

			return "messages successfully retrived";
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@DeleteMapping("/{key}")
	public String delete(@PathVariable String key) {
		try {
			String deleteMessageFromRedis = rabbitMqConsumerService.deleteMessageFromRedis(key);
			return deleteMessageFromRedis;
		} catch (Exception e) {
			return e.getMessage();
		}

	}

}

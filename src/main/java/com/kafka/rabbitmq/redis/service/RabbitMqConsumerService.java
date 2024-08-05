package com.kafka.rabbitmq.redis.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqConsumerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMqConsumerService.class);

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@RabbitListener(queues = "my-queue")
	public void consumeMessages(String message) {

		try {
			System.out.println(message);

			LOGGER.info("Messages are consumed successfully {}", message);
			String key = "message" + message.hashCode();

			if (redisTemplate.hasKey(key)) {
				LOGGER.info("KEY IS ALREADY PRESENT IN REDIS {}", key);
			} else {
				redisTemplate.opsForValue().set(key, message);
			}

		} catch (Exception e) {
			LOGGER.error("Something went wrong! {}", e.getMessage());
		}

	}

	public String getMessageFromRedis(String key) {

		String data = redisTemplate.opsForValue().get(key);

		System.out.println(data);

		return data;
	}

	public String deleteMessageFromRedis(String key) {

		String andDelete = redisTemplate.opsForValue().getAndDelete(key);

		System.out.println(andDelete);

		return "message deleted successfully";
	}

}

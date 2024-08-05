package com.kafka.rabbitmq.redis.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

	private final static Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@KafkaListener(topics = "my-topic", groupId = "my-group")
	public void listenKafkaMessage(String message) {

		try {

			System.out.println(message);
			LOGGER.info("MESSAGES CONSUMED SUCCESSFULLY");

			String key = "message" + message.hashCode();

			if (redisTemplate.hasKey(key)) {
				LOGGER.info("KEY IS ALREADY PRESENT IN REDIS {}", key);
			} else {
				redisTemplate.opsForValue().set(key, message);
				LOGGER.info("MESSAGE SAVED IN REDIS DATABASE");
			}

		} catch (Exception e) {

			LOGGER.error("Something went wrong {}", e.getMessage());
		}

	}

	public String getMessageFromRedis(String key) {

		String data = redisTemplate.opsForValue().get(key);

		System.out.println(data);

		return data;
	}

}

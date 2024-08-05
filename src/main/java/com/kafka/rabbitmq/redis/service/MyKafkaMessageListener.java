package com.kafka.rabbitmq.redis.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;

public class MyKafkaMessageListener implements MessageListener<String, String> {

	@Override
	public void onMessage(ConsumerRecord<String, String> data) {

		System.out.println(data.key() + "   " + data.value());

	}

}

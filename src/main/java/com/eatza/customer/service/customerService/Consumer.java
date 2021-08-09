package com.eatza.customer.service.customerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.eatza.kafka.feedback.Feedback;

@Service
public class Consumer {

	private final Logger logger = LoggerFactory.getLogger(Consumer.class);

	@KafkaListener(topics = "feedbackRecived", groupId = "group_id")
	//public void consume(String note) -- for kafka String
	public void consume(Feedback gotFeedback) 
	{
		logger.info(String.format("Feedback recived -> %s", gotFeedback));
	}

}



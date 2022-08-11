package com.carlos.stockconsumer.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import constants.RabbitMQConstants;
import dtos.StockDTO;

@Component
public class StockConsumer {

	@RabbitListener(queues = RabbitMQConstants.STOCK_QUEUE)
	private void consumer(String message) throws JsonProcessingException, InterruptedException {
		StockDTO stockDTO = new ObjectMapper().readValue(message, StockDTO.class);

		System.out.println(stockDTO.productCode);
		System.out.println(stockDTO.quantity);
		System.out.println("-------------------------");

		throw new IllegalArgumentException("Argumento inválido");
	}
}

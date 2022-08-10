package com.carlos.stockconsumer.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import constants.RabbitMQConstants;
import dtos.StockDTO;

@Component
public class StockConsumer {

	@RabbitListener(queues = RabbitMQConstants.STOCK_QUEUE)
	private void consumer(StockDTO stockDTO) {
		System.out.println(stockDTO.productCode);
		System.out.println(stockDTO.quantity);
		System.out.println("-------------------------");
	}
}

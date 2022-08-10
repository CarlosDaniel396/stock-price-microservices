package com.carlos.stockprice.connection;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

import constants.RabbitMQConstants;

@Component
public class RabbitMQConnection {
	private static final String EXCHANGE_NAME = "amq.direct";

	private AmqpAdmin amqpAdmin;

	public RabbitMQConnection(AmqpAdmin amqpAdmin) {
		this.amqpAdmin = amqpAdmin;
	}

	private Queue queue(String queueName) {
		return new Queue(queueName, true, false, false);
	}

	private DirectExchange directExchange() {
		return new DirectExchange(EXCHANGE_NAME);
	}

	private Binding binding(Queue queue, DirectExchange directExchange) {
		return new Binding(queue.getName(), Binding.DestinationType.QUEUE, directExchange.getName(), queue.getName(),
				null);
	}

	@PostConstruct
	private void add() {
		Queue stock_queue = this.queue(RabbitMQConstants.STOCK_QUEUE);
		Queue price_queue = this.queue(RabbitMQConstants.PRICE_QUEUE);

		DirectExchange directExchange = this.directExchange();

		Binding stock_binding = this.binding(stock_queue, directExchange);
		Binding price_binding = this.binding(price_queue, directExchange);

		this.amqpAdmin.declareQueue(stock_queue);
		this.amqpAdmin.declareQueue(price_queue);

		this.amqpAdmin.declareExchange(directExchange);

		this.amqpAdmin.declareBinding(stock_binding);
		this.amqpAdmin.declareBinding(price_binding);
	}
}

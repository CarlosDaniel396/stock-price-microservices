package com.carlos.stockconsumer.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.DirectRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.listener.DirectMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.FatalExceptionStrategy;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ErrorHandler;

import com.carlos.stockconsumer.consumer.CustomErrorStrategy;
import com.carlos.stockconsumer.exceptions.ExceptionHandler;

@Configuration
public class RabbitMQConfig {

	@Bean
	public RabbitListenerContainerFactory<DirectMessageListenerContainer> rabbitListenerContainerFactory(
			ConnectionFactory connectionFactory) {
		DirectRabbitListenerContainerFactory factory = new DirectRabbitListenerContainerFactory();

		factory.setConnectionFactory(connectionFactory);
		factory.setAcknowledgeMode(AcknowledgeMode.AUTO);

		factory.setPrefetchCount(4);

		factory.setErrorHandler(errorHandler());

		return factory;
	}

	@Bean
	public FatalExceptionStrategy customErrorStrategy() {
		return new CustomErrorStrategy();
	}

	@Bean
	public ErrorHandler errorHandler() {
		return new ConditionalRejectingErrorHandler(customErrorStrategy());
	}
}

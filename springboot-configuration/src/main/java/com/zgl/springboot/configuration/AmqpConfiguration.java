package com.zgl.springboot.configuration;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author zgl
 * @date 2019/4/1 上午10:59
 */
@Configuration
@Data
@Slf4j
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class AmqpConfiguration {

	private String host;

	private int port;

	private String username;

	private String password;

	private String virtualHost;

	private String queueName = "zgl-queue";

	private String routingKey = "zgl-key";

	private String exchangeName = "zgl-exchange";

	/**
	 * 可以不用写
	 */
	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setHost(host);
		connectionFactory.setPort(port);
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		connectionFactory.setVirtualHost(virtualHost);
		return connectionFactory;
	}

	@Bean
	public Queue zglQueue() {
		log.info("mq队列初始化");
		return new Queue(queueName, true);
	}

	@Bean
	public DirectExchange zglExchange() {
		log.info("mq交换机初始化");
		return new DirectExchange(exchangeName, true, false);
	}

	@Bean
	public Binding bindingQueue() {
		return BindingBuilder.bind(zglQueue()).to(zglExchange()).with(routingKey);
	}

	@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
		return new RabbitAdmin(connectionFactory);
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate template = new RabbitTemplate(connectionFactory());
		template.setConfirmCallback((CorrelationData correlationData, boolean ack, String cause) -> {
			log.info("消息投入到交换机id:{}", correlationData.getId());
			if (!ack) {
				log.error("Fail to send message to exchange!");
			}
		});
		template.setReturnCallback((Message message, int replyCode, String replyText, String exchange, String routingKey) ->
				log.info("return callback happened"));
		template.setMandatory(true);
		return template;
	}

}
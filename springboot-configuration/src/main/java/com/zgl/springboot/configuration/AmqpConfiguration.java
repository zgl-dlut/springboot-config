package com.zgl.springboot.configuration;

import com.zgl.springboot.common.Constant;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zgl
 * @date 2019/4/1 上午10:59
 */
@Configuration
@Data
@Slf4j
public class AmqpConfiguration {

	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
		factory.setConnectionFactory(connectionFactory);
		return factory;
	}



	@Bean
	public Queue zglQueue() {
		log.info("mq队列初始化");
		return new Queue(Constant.queueName, true);
	}

	@Bean
	public DirectExchange zglExchange() {
		log.info("mq交换机初始化");
		return new DirectExchange(Constant.exchangeName, true, false);
	}

	@Bean
	public Binding bindingQueue() {
		return BindingBuilder.bind(zglQueue()).to(zglExchange()).with(Constant.routingKey);
	}

	@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
		return new RabbitAdmin(connectionFactory);
	}

	@Bean(name = "zglRabbitTemplate")
	//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.setConfirmCallback((correlationData, ack, cause) -> {
			if (!ack) {
				log.info("Fail to send message to exchange!");
			}
			log.info("ConfirmCallback:     相关数据：{}", correlationData);
			log.info("ConfirmCallback:     确认情况：{}", ack);
			log.info("ConfirmCallback:     原因：{}", cause);
		});

		template.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
			log.info("ReturnCallback:     消息：{}", message);
			log.info("ReturnCallback:     回应码：{}", replyCode);
			log.info("ReturnCallback:     回应信息：{}", replyText);
			log.info("ReturnCallback:     交换机：{}", exchange);
			log.info("ReturnCallback:     路由键：{}", routingKey);
		});
		template.setMandatory(true);
		return template;
	}

}
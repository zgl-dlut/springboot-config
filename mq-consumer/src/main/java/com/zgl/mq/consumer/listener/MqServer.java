package com.zgl.mq.consumer.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author zgl
 * @date 2019/12/17 下午2:56
 */
@Component
@RabbitListener(queues = "zgl-queue")
public class MqServer {

	@RabbitHandler
	public void process(String message) {

	}
}
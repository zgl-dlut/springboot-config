package com.zgl.springboot.service;

import com.zgl.springboot.common.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zgl
 * @date 2019/12/12 下午8:45
 */
@Slf4j
@Component
public class MqClient {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public String sendMessage(String message) {
		rabbitTemplate.convertAndSend(Constant.exchangeName, Constant.routingKey, message);
		return "ok";
	}
}
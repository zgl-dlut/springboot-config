package com.zgl.springboot.service;

import com.alibaba.fastjson.JSON;
import com.zgl.springboot.common.Constant;
import com.zgl.springboot.domain.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zgl
 * @date 2019/12/12 下午8:45
 */
@Slf4j
@Component
public class MqClient {

	@Autowired
	@Qualifier("zglRabbitTemplate")
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private MessageService messageService;

	public void sendMessage(String message) {
		rabbitTemplate.convertAndSend(Constant.exchangeName, Constant.routingKey, message);
	}

	public String produce(int count) {
		List<Message> messageList = messageService.queryMessageList(count);
		for (Message message : messageList) {
			this.sendMessage(JSON.toJSONString(message));
			log.info("消息生产:{}", JSON.toJSONString(message));
		}
		return "ok";
	}
}
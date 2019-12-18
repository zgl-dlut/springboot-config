package com.zgl.springboot.controller;

import com.zgl.springboot.common.Constant;
import com.zgl.springboot.service.MessageService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author zgl
 * @date 2019/12/12 下午7:49
 */
@RestController
@RequestMapping("/mq")
public class MessageSendController {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private MessageService messageService;

	@GetMapping("/sendMessage")
	public String sendMessage() {
		String messageId = String.valueOf(UUID.randomUUID());
		String messageData = "test message, hello!";
		String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		Map<String,Object> map=new HashMap<>();
		map.put("messageId",messageId);
		map.put("messageData",messageData);
		map.put("createTime",createTime);
		//将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
		rabbitTemplate.convertAndSend(Constant.exchangeName, Constant.routingKey, map);
		return "ok";
	}

	@GetMapping("/createMessage")
	public void createMessage(@RequestParam("count") int count) {
		messageService.batchInsertMessage(count);
	}
}
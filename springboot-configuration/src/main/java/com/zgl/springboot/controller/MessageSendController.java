package com.zgl.springboot.controller;

import com.zgl.springboot.common.Constant;
import com.zgl.springboot.domain.Message;
import com.zgl.springboot.service.MessageService;
import com.zgl.springboot.service.MqClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
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
	private MessageService messageService;

	@Autowired
	private MqClient mqClient;

	@GetMapping("/sendMessage")
	public String sendMessage(@RequestParam("count") int count) {
		return mqClient.produce(count);
	}

	@GetMapping("/createMessage")
	public void createMessage(@RequestParam("count") int count) {
		messageService.batchInsertMessage(count);
	}

	@GetMapping("/queryMessageList")
	public List<Message> queryMessageList(@RequestParam("count") int count) {
		return messageService.queryMessageList(count);
	}
}
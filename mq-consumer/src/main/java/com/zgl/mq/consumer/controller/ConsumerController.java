package com.zgl.mq.consumer.controller;

import com.zgl.mq.consumer.dto.MessageDto;
import com.zgl.mq.consumer.service.MessageProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zgl
 * @date 2019/12/18 下午3:30
 */
@RestController
@RequestMapping("/consume")
public class ConsumerController {

	@Autowired
	private MessageProcessService messageProcessService;

	@GetMapping("/inOrder")
	public List<MessageDto> consumeInOrder() {
		return messageProcessService.consumeInOrder();
	}
}

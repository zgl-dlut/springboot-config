package com.zgl.mq.consumer.service.impl;

import com.zgl.mq.consumer.dto.MessageDto;
import com.zgl.mq.consumer.service.MessageProcessService;
import com.zgl.springboot.common.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zgl
 * @date 2019/12/18 下午3:37
 */
@Service
public class MessageProcessServiceImpl implements MessageProcessService {

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Override
	public List<MessageDto> consumeInOrder() {
		List<MessageDto> messageList = new ArrayList<>();
		int count = 0;
		for (int i = 0; i < 100; i++) {
			String message = redisTemplate.opsForList().rightPop("age" + String.valueOf(i));
			while (StringUtils.isNotBlank(message)) {
				MessageDto messageDto = JsonUtils.json2Bean(message, MessageDto.class);
				count++;
				messageList.add(messageDto);
				if (count == 100) {
					return messageList;
				}
				message = redisTemplate.opsForList().rightPop("age" + String.valueOf(i));
			}
		}
		return messageList;
	}
}

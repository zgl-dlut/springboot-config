package com.zgl.mq.consumer.service;

import com.zgl.mq.consumer.dto.MessageDto;

import java.util.List;

/**
 * @author zgl
 * @date 2019/12/18 下午3:35
 */
public interface MessageProcessService {
	/**
	 * 顺序消费,每次消费100个
	 * @return
	 */
	List<MessageDto> consumeInOrder();

}
package com.zgl.springboot.listener;

import com.rabbitmq.client.Channel;
import com.zgl.springboot.common.util.JsonUtils;
import com.zgl.springboot.domain.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * @author zgl
 * @date 2019/12/17 下午2:56
 */
@Slf4j
@Component
public class MqServer {

	@Autowired
	private StringRedisTemplate redisTemplate;

	@RabbitListener(queues = "zgl-queue")
	public void process(String json, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
		log.info("消费线程名称:{}", Thread.currentThread().getName());
		Message messageDto = JsonUtils.json2Bean(json, Message.class);
		try {
			//redisTemplate.opsForList().leftPush("age" + String.valueOf(messageDto.getAge()), json);
			log.info("消息消费:{}", messageDto);
			channel.basicAck(deliveryTag, false);
		} catch (IOException e) {
			log.error("消息id:{}重复消费, 消费线程:{}", messageDto.getId(), Thread.currentThread().getName());
			try {
				//手动nack消息
				channel.basicNack(deliveryTag, false, false);
			} catch (IOException e1) {
				log.error(e1.getMessage());
			}
		}

	}
}
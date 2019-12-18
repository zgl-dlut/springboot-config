package com.zgl.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zgl.springboot.domain.Message;
import com.zgl.springboot.mapper.MessageMapper;
import com.zgl.springboot.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zgl
 * @since 2019-12-17
 */
@Service
@Slf4j
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {
	@Override
	public void batchInsertMessage(int count) {
		int taskCount = this.getBatchTaskCount(count);
		int lastTaskNum = this.getLastTaskNum(taskCount, count);
		List<CompletableFuture<Void>> completableFutureList = new ArrayList<>();
		for (int i = 1; i < taskCount; i++) {
			completableFutureList.add(CompletableFuture.runAsync(() -> this.createMessageList(null)));
		}
		completableFutureList.add(CompletableFuture.runAsync(() -> this.createMessageList(lastTaskNum)));
		CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[0])).join();
	}

	private int getBatchTaskCount(int size) {
		return size / 100 + (size % 100 == 0 ? 0 : 1);
	}
	private int getLastTaskNum(int taskCount, int taskSize) {
		return taskCount == 1 ? taskSize % 100 : taskSize - 100 * (taskCount - 1);
	}

	private void createMessageList(Integer taskNum) {
		List<Message> messageList = new ArrayList<>();
		int createNum = taskNum == null ? 100 : taskNum;
		for (int i = 0; i < createNum; i++) {
			Message message =  Message.builder().age(i).name("zgl" + i).sex(1).created(LocalDate.now()).build();
			messageList.add(message);
		}
		this.saveBatch(messageList);
	}
	public static void main(String[] args) {
		int count = 99;
		int count1 = 301;
		System.out.println(new MessageServiceImpl().getBatchTaskCount(count));
		System.out.println(new MessageServiceImpl().getBatchTaskCount(count1));
		System.out.println(new MessageServiceImpl().getLastTaskNum(1, count));
		System.out.println(new MessageServiceImpl().getLastTaskNum(4, count1));
	}
}

package com.zgl.springboot.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zgl.springboot.domain.Message;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zgl
 * @since 2019-12-17
 */
public interface MessageService extends IService<Message> {

	void batchInsertMessage(int count);
}

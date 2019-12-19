package com.zgl.springboot.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zgl.springboot.domain.Message;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zgl
 * @since 2019-12-17
 */
public interface MessageService extends IService<Message> {

	/**
	 * 批量造数据
	 */
	void batchInsertMessage(int count);

	/**
	 * 获取年龄有序的列表
	 * @return
	 */
	List<Message> queryMessageList(int count);

}

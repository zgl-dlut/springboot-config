package com.zgl.mybatis.plus.service.impl;

import com.zgl.mybatis.plus.entity.Message;
import com.zgl.mybatis.plus.mapper.MessageMapper;
import com.zgl.mybatis.plus.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zgl
 * @since 2019-12-17
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

}

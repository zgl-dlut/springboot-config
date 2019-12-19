package com.zgl.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zgl.springboot.domain.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zgl
 * @since 2019-12-17
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {

	Message getOneByAge(@Param("age") int age);
}

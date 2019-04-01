package com.zgl.springboot.mapper;

		import tk.mybatis.mapper.common.Mapper;
		import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author zgl
 * @date 2019/4/1 上午10:05
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
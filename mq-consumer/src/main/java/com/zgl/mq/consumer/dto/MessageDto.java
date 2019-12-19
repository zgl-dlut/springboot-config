package com.zgl.mq.consumer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zgl
 * @date 2019/12/18 下午3:11
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private Integer sex;

	private Integer age;

	private Date created;

}
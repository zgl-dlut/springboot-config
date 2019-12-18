package com.zgl.springboot.domain;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zgl
 * @since 2019-12-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Regist对象", description="")
public class Regist implements Serializable {

    private static final long serialVersionUID=1L;

    private String id;

    private String classId;

    @ApiModelProperty(value = "报名类型(0：正常报名；1：续报；2：跨班报；3：候补报名；4：预报名；)")
    private Integer isContinue;

    @ApiModelProperty(value = "报名状态(0：正常；1：已撤单；2：已退班；3：续报已放入回收站;4:已转班)")
    private Integer deleted;

    private String studentId;

    @ApiModelProperty(value = "是否已缴费(0：未缴费；1：已缴费;2:缴费处理中;3:已退费;4:缴费中(班级处于待缴费订单中时的状态))")
    private Integer isPay;

    @ApiModelProperty(value = "报名方式(0：前台报名；1：网上报名；2：电话报名;；3：android；4：ios；5：crm报名；6：微信活动；7：微信；8：百度直达号)")
    private Integer regWay;

    @ApiModelProperty(value = "报班课次")
    private Integer classNo;

    @ApiModelProperty(value = "补课课次")
    private Integer addClassQuantity;

    @ApiModelProperty(value = "是否是全价课（0：否；1：是）")
    private Integer isAllCourse;

    @ApiModelProperty(value = "缴费截止时间")
    private LocalDateTime payEnddate;

    @ApiModelProperty(value = "续报源班级ID(作废)")
    private String sourceClassId;

    @ApiModelProperty(value = "是否测试数据(0：否；1：是；)")
    private Integer isTest;

    @ApiModelProperty(value = "是否统计(0：否；1：是；)")
    private Integer isStatistic;

    @ApiModelProperty(value = "听课证编码")
    private String listenerCode;

    @ApiModelProperty(value = "听课证Id")
    private String listenercardId;

    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人")
    private String creator;

    @ApiModelProperty(value = "注释")
    private String remark;

    private Integer version;

    @ApiModelProperty(value = "是否新生报名")
    private Integer isNewStu;

    @ApiModelProperty(value = "此次报名已上课次")
    private Integer passedCount;

    @ApiModelProperty(value = "记录预留转班次数")
    private Integer reservedTimes;

    @ApiModelProperty(value = "是否预留转班（0否，1是）")
    private Integer isReserved;

    @ApiModelProperty(value = "预转班源班级ID")
    private String reserveSourceClassId;

    @ApiModelProperty(value = "预留转班名额截止日期")
    private LocalDateTime reservedChangeClassesDeadline;

    @ApiModelProperty(value = "是否领取书0:未领取1:领取")
    private Integer isReceiptBooks;

    @ApiModelProperty(value = "修改人id")
    private String modifier;

    @ApiModelProperty(value = "修改日期")
    private LocalDateTime modifyTime;

    @ApiModelProperty(value = "是否为测试数据0：否；1：是")
    private Integer biTest;

    @ApiModelProperty(value = "缴费时间")
    private LocalDateTime payDate;

    @ApiModelProperty(value = "准考证编号")
    private String candidateNo;

    @ApiModelProperty(value = "领取准考证服务中心Id")
    @TableField("candidate_serviceCenter_id")
    private String candidateServicecenterId;

    @ApiModelProperty(value = "坐位号")
    private Integer seat;

    @ApiModelProperty(value = "是否发送短信（0否，1是）")
    private Integer isSendSms;

    @ApiModelProperty(value = "在线外教排课id")
    private String beiliCourseId;

    @ApiModelProperty(value = "环迅方排课no")
    private Integer isBeiliCourseNo;

    @ApiModelProperty(value = "是否计入crm成单统计（0:不计入,1:计入）")
    private Integer crmStatistic;

    @ApiModelProperty(value = "报名渠道-活动名称")
    private String activity;

    @ApiModelProperty(value = "限制方式 0:强制 1:推荐 2:无限制")
    private Integer associatedCommonClassType;

    @ApiModelProperty(value = "配对的普通班报名ID")
    private String associatedCommonClassRegId;

    @ApiModelProperty(value = "删除人")
    private String deleter;

    @ApiModelProperty(value = "删除时间")
    private LocalDateTime deletedTime;

    private String orgCode;

    @ApiModelProperty(value = "所报班级的年份，如发生转班需要更新此字段")
    private Integer year;

    @ApiModelProperty(value = "班级类型")
    private Integer classType;

    @ApiModelProperty(value = "新字段,退费时间,老数据可能没有这个值")
    private LocalDateTime returnTime;

    private LocalDateTime tmTime;

    private String studentUid;

    private String termId;

    private String stuOrgCode;

    @ApiModelProperty(value = "已删除数据的唯一索引")
    private String deletedSign;

    private LocalDateTime lockedTime;

    private Integer lockedStatus;

    @ApiModelProperty(value = "请求ID")
    private String requestId;


}

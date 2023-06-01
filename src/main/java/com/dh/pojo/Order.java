package com.dh.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @TableName t_order
 */
@TableName(value ="t_order")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {
    /**
     *
     */

    public static final String ORDERTYPE_TELEPHONE = "电话预约";
    public static final String ORDERTYPE_WEIXIN = "微信预约";
    public static final String ORDERSTATUS_YES = "已到诊";
    public static final String ORDERSTATUS_NO = "未到诊";


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 员会id
     */
    @TableField(value = "member_id")
    private Integer memberId;

    /**
     * 约预日期
     */
    @TableField(value = "orderDate")
    private Date orderdate;

    /**
     * 约预类型 电话预约/微信预约
     */
    @TableField(value = "orderType")
    private String ordertype;

    /**
     * 预约状态（是否到诊）
     */
    @TableField(value = "orderStatus")
    private String orderstatus;

    /**
     * 餐套id
     */
    @TableField(value = "setmeal_id")
    private Integer setmealId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Order(Integer id, Date date, String orderType, String orderstatusNo, int setmealId) {
        this.memberId = id;
        this.orderdate = date;
        this.ordertype = orderType;
        this.orderstatus = orderstatusNo;
        this.setmealId = setmealId;

    }
}
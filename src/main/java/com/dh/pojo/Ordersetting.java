package com.dh.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName t_ordersetting
 */
@TableName(value ="t_ordersetting")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Ordersetting implements Serializable {

    public Ordersetting(Date orderDate, int number) {
        this.orderdate = orderDate;
        this.number = number;
    }
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 约预日期
     */
    @TableField(value = "orderDate")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date orderdate;

    /**
     * 可预约人数
     */
    @TableField(value = "number")
    private Integer number;

    /**
     * 已预约人数
     */
    @TableField(value = "reservations")
    private Integer reservations;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
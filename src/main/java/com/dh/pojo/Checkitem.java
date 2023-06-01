package com.dh.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName t_checkitem
 */
@TableName(value ="t_checkitem")
@Data
public class Checkitem implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    @TableField(value = "code")
    private String code;

    /**
     * 
     */
    @TableField(value = "name")
    private String name;

    /**
     * 
     */
    @TableField(value = "sex")
    private String sex;

    /**
     * 
     */
    @TableField(value = "age")
    private String age;

    /**
     * 
     */
    @TableField(value = "price")
    private Double price;

    /**
     * 查检项类型,分为检查和检验两种
     */
    @TableField(value = "type")
    private String type;

    /**
     * 
     */
    @TableField(value = "attention")
    private String attention;

    /**
     * 
     */
    @TableField(value = "remark")
    private String remark;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
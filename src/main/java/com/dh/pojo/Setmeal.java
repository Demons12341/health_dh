package com.dh.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @TableName t_setmeal
 */
@TableName(value ="t_setmeal")
@Data
@ToString
public class Setmeal implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    @TableField(value = "name")
    private String name;

    /**
     * 
     */
    @TableField(value = "code")
    private String code;

    /**
     * 
     */
    @TableField(value = "helpCode")
    private String helpcode;

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
     * 
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 
     */
    @TableField(value = "attention")
    private String attention;

    /**
     * 
     */
    @TableField(value = "img")
    private String img;

    @TableField(exist = false)
    private List<Checkgroup> checkGroups;//体检套餐对应的检查组，多对多关系

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
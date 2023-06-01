package com.dh.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @TableName t_checkgroup
 */
@TableName(value ="t_checkgroup")
@Data
public class Checkgroup implements Serializable {
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
    @TableField(value = "remark")
    private String remark;

    /**
     * 
     */
    @TableField(value = "attention")
    private String attention;

    @TableField(exist = false)
    private List<Checkitem> checkItems;//一个检查组合包含多个检查项

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
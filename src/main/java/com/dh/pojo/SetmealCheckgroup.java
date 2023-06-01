package com.dh.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName t_setmeal_checkgroup
 */
@TableName(value ="t_setmeal_checkgroup")
@Data
public class SetmealCheckgroup implements Serializable {
    /**
     * 
     */
    private Integer setmealId;

    /**
     * 
     */
    private Integer checkgroupId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public SetmealCheckgroup(Integer id, Integer checkGroupId) {
        this.checkgroupId=checkGroupId;
        this.setmealId=id;
    }
}
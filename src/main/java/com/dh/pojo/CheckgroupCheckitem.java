package com.dh.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName t_checkgroup_checkitem
 */
@TableName(value ="t_checkgroup_checkitem")
@Data
public class CheckgroupCheckitem implements Serializable {
    /**
     * 
     */
    private Integer checkgroupId;

    /**
     * 
     */
    private Integer checkitemId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


    public CheckgroupCheckitem(Integer checkGroupId , Integer checkitemId){
        this.checkgroupId=checkGroupId;
        this.checkitemId=checkitemId;

    }
}
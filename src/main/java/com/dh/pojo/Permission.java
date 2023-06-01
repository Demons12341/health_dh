package com.dh.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @TableName t_permission
 */
@TableName(value ="t_permission")
@Data
public class Permission implements Serializable {
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
    @TableField(value = "keyword")
    private String keyword;

    /**
     * 
     */
    @TableField(value = "description")
    private String description;

    @TableField(exist = false)
    private Set<Role> roles = new HashSet<Role>(0);

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
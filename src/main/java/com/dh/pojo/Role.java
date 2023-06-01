package com.dh.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 
 * @TableName t_role
 */
@TableName(value ="t_role")
@Data
public class Role implements Serializable {
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
    private Set<User> users = new HashSet<User>(0);

    @TableField(exist = false)
    private Set<Permission> permissions = new HashSet<Permission>(0);

    @TableField(exist = false)
    private LinkedHashSet<Menu> menus = new LinkedHashSet<Menu>(0);

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
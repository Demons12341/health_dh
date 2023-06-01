package com.dh.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * @TableName t_menu
 */
@TableName(value ="t_menu")
@Data
public class Menu implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 菜单名
     */
    @TableField(value = "name")
    private String name;

    /**
     *  url
     */
    @TableField(value = "linkUrl")
    private String linkurl;

    /**
     * 路径
     */
    @TableField(value = "path")
    private String path;

    /**
     * 优先级
     */
    @TableField(value = "priority")
    private Integer priority;

    /**
     * 图标
     */
    @TableField(value = "icon")
    private String icon;

    /**
     * 描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 
     */
    @TableField(value = "parentMenuId")
    private Integer parentmenuid;

    /**
     * 
     */
    @TableField(exist = false)
    private Set<Role> roles = new HashSet<Role>(0);//角色集合

    @TableField(exist = false)
    private List<Menu> children = new ArrayList<>();//子菜单集合

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
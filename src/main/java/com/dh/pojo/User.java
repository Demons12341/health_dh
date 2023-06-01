package com.dh.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @TableName t_user
 */
@TableName(value ="t_user")
@Data
public class User implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    @TableField(value = "birthday")
    private Date birthday;

    /**
     * 
     */
    @TableField(value = "gender")
    private String gender;

    /**
     * 
     */
    @TableField(value = "username")
    private String username;

    /**
     * 
     */
    @TableField(value = "password")
    private String password;

    /**
     * 
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 
     */
    @TableField(value = "station")
    private String station;

    /**
     * 
     */
    @TableField(value = "telephone")
    private String telephone;

    @TableField(exist = false)
    private Set<Role> roles = new HashSet<Role>(0);//对应角色集合

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
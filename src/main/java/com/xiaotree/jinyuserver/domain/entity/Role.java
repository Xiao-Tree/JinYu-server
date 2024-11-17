package com.xiaotree.jinyuserver.domain.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author xiaotree
 * @create 2024-07-14 22:49:42
 * @description Role
 */

@Table(value = "xit_role")
@Data
public class Role {

    @Id(keyType = KeyType.Auto,before = false)
    private Integer id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 权限标识
     */
    private String key;

    /**
     * 备注
     */
    private String comment;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Timestamp createAt;

    /**
     * 更新时间
     */
    private Timestamp uptateAt;

}

package com.xiaotree.jinyuserver.domain.vo;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class RoleInfo {
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
    private Timestamp updateAt;

    private List<Integer> menus;
}

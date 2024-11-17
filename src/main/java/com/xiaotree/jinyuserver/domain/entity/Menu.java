package com.xiaotree.jinyuserver.domain.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;


/**
 * @author xiaotree
 * @create 2024-07-15 00:13:52
 * @description Menu
 */

@Table(value = "xit_menu")
@Data
public class Menu {
    @Id(keyType = KeyType.Auto,before = false)
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 父级id
     */
    private Integer parentId;

    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * 菜单类型（D目录 M菜单 B按钮）
     */
    private String type;

    /**
     * 权限标识
     */
    private String perm;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 是否在菜单中隐藏
     */
    private Integer hideInMenu;

    /**
     * 是否缓存
     */
    private Integer keepAlive;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 备注
     */
    private String comment;

}

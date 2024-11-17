package com.xiaotree.jinyuserver.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class BaseMenu{
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 父级id
     */
    private Integer parentId;

    private List<BaseMenu> children;
}

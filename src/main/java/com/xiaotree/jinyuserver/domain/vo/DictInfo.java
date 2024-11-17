package com.xiaotree.jinyuserver.domain.vo;

import com.xiaotree.jinyuserver.domain.entity.DictData;
import lombok.Data;

import java.util.List;

@Data
public class DictInfo{
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 标识符
     */
    private String key;

    /**
     * 状态
     */
    private Boolean status;

    /**
     * 备注
     */
    private String comment;

    private List<DictData> values;

    private List<DictData> addValues;
    private List<DictData> deleteValues;
}

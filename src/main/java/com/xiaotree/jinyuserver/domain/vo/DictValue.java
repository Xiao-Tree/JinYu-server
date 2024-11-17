package com.xiaotree.jinyuserver.domain.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class DictValue implements Serializable {
    /**
     * 字典标签
     */
    private String label;

    /**
     * 字典值
     */
    private Integer value;
}

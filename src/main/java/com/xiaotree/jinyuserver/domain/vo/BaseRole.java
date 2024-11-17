package com.xiaotree.jinyuserver.domain.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseRole implements Serializable {
    private Integer id;
    private String key;
    private String name;
}

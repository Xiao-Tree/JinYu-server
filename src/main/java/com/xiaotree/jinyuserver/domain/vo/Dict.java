package com.xiaotree.jinyuserver.domain.vo;

import com.xiaotree.jinyuserver.domain.entity.DictType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class Dict implements Serializable {
    private DictType type;
    private List<DictValue> values;
}

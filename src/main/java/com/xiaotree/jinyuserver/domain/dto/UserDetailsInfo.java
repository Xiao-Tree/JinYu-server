package com.xiaotree.jinyuserver.domain.dto;

import com.xiaotree.jinyuserver.domain.vo.BaseRole;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserDetailsInfo implements Serializable {
    private Integer id;
    private String avatar;
    private String token;
    private List<BaseRole> roles;
}

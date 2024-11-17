package com.xiaotree.jinyuserver.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
public class UpdateUser extends CreateUser {
    private Integer id;
    private Integer status;
    private List<Integer> originalRoleIds;
    private String originalAvatar;
}

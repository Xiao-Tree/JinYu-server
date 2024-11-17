package com.xiaotree.jinyuserver.domain.vo;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class UserInfo {
    private Integer id;
    private String username;
    private Integer sex;
    private List<Integer> roleIds;
    private Integer deptId;
    private String phoneNum;
    private String email;
    private String avatar;
    private Integer status;
    private Timestamp createAt;
}

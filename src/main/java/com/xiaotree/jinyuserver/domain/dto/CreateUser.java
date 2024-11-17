package com.xiaotree.jinyuserver.domain.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class CreateUser {
    /**
     * 登录名
     */
    protected String username;

    /**
     * 密码
     */
    protected String password;

    /**
     * 性别
     */
    protected Integer sex;

    /**
     * 角色id列表
     */
    protected List<Integer> roleIds;

    /**
     * 部门id
     */
    protected Integer deptId;

    /**
     * 邮箱
     */
    protected String email;

    /**
     * 头像
     */
    protected MultipartFile avatar;

    /**
     * 手机号码
     */
    protected String phoneNum;
}

package com.xiaotree.jinyuserver.domain.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author xiaotree
 * @create 2024-07-14 14:35:25
 * @description User
 */

@Table(value = "xit_user")
@Data
public class User {

    @Id(keyType = KeyType.Auto,before = false)
    private Integer id;

    /**
     * 姓名
     */
    private String nickname;

    /**
     * 登录名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 部门id
     */
    private Integer deptId;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 手机号码
     */
    private String phoneNum;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Timestamp createAt;

    /**
     * 更新时间
     */
    private Timestamp updateAt;

}

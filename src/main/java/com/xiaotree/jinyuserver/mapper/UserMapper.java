package com.xiaotree.jinyuserver.mapper;

import com.mybatisflex.core.BaseMapper;
import com.xiaotree.jinyuserver.domain.entity.User;

import java.util.List;


public interface UserMapper extends BaseMapper<User> {
    Integer addRoleIds(Integer userId, List<Integer> roleIds);

    Integer addUser(User user);

    Integer updateUserId(Integer originalId,Integer newId);
}

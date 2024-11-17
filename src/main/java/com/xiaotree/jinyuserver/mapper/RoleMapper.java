package com.xiaotree.jinyuserver.mapper;

import com.mybatisflex.core.BaseMapper;
import com.xiaotree.jinyuserver.domain.entity.Role;
import com.xiaotree.jinyuserver.domain.vo.BaseRole;

import java.util.List;


public interface RoleMapper extends BaseMapper<Role> {
    List<Integer> selectUserRoleIds(Integer userId);
    Integer addRoleIds(Integer userId,List<Integer> roleIds);
    Integer removeRoleIds(Integer userId,List<Integer> roleIds);
    List<BaseRole> getRolesByUserId(Integer userId);
    List<String> selectPermsByRoleId(Integer roleId);
    List<String> selectPermsByRoleIds(List<Integer> roleIds);
    Integer addRoleMenus(Integer roleId,List<Integer> menus);
    Integer removeRoleMenus(Integer roleId,List<Integer> menus);
}

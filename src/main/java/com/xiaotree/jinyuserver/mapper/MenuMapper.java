package com.xiaotree.jinyuserver.mapper;

import com.mybatisflex.core.BaseMapper;
import com.xiaotree.jinyuserver.domain.entity.Menu;
import com.xiaotree.jinyuserver.domain.vo.BaseMenu;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {
    List<BaseMenu> selectAllBaseMenu();
    List<Integer> selectRoleMenus(Integer roleId);
}

package com.xiaotree.jinyuserver.service;

import com.mybatisflex.core.paginate.Page;
import com.xiaotree.jinyuserver.domain.entity.DictData;
import com.xiaotree.jinyuserver.domain.entity.DictType;
import com.xiaotree.jinyuserver.domain.entity.Role;
import com.xiaotree.jinyuserver.domain.vo.BaseMenu;
import com.xiaotree.jinyuserver.domain.vo.DictInfo;
import com.xiaotree.jinyuserver.domain.vo.RoleInfo;

import java.util.HashMap;
import java.util.List;

public interface SystemService {
    Page<DictType> getDictTypeList(Integer page, Integer pageSize, Integer totalRow);

    List<DictData> getDictDataList(Integer dictId);

    Boolean addDict(DictType dictType, List<DictData> values);

    Boolean deleteDicts(List<Integer> dictIds);

    Boolean updateDict(DictInfo dictInfo);

    Page<RoleInfo> getRoles(Integer page, Integer pageSize,
                            Integer totalRow);

    Boolean addRole(Role role);

    Boolean deleteRoles(List<Integer> roleIds);

    List<BaseMenu> getAllMenu();

    Boolean updateRole(Role role, Integer roleId);

    Boolean updateRoleMenus(Integer roleId, HashMap<String, List<Integer>> menulist);
}

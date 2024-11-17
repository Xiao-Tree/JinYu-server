package com.xiaotree.jinyuserver.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.xiaotree.jinyuserver.domain.entity.DictData;
import com.xiaotree.jinyuserver.domain.entity.DictType;
import com.xiaotree.jinyuserver.domain.entity.Role;
import com.xiaotree.jinyuserver.domain.vo.BaseMenu;
import com.xiaotree.jinyuserver.domain.vo.DictInfo;
import com.xiaotree.jinyuserver.domain.vo.RoleInfo;
import com.xiaotree.jinyuserver.handler.ServiceException;
import com.xiaotree.jinyuserver.mapper.DictMapper;
import com.xiaotree.jinyuserver.mapper.MenuMapper;
import com.xiaotree.jinyuserver.mapper.RoleMapper;
import com.xiaotree.jinyuserver.service.SystemService;
import com.xiaotree.jinyuserver.util.Tools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Transactional
@Slf4j
@Service
public class SystemServiceImpl implements SystemService {
    private final DictMapper dictMapper;
    private final RoleMapper roleMapper;
    private final MenuMapper menuMapper;

    public SystemServiceImpl(DictMapper dictMapper, RoleMapper roleMapper, MenuMapper menuMapper) {
        this.dictMapper = dictMapper;
        this.roleMapper = roleMapper;
        this.menuMapper = menuMapper;
    }

    @Override
    public Page<DictType> getDictTypeList(Integer page, Integer pageSize, Integer totalRow) {
        Page<DictType> searchPage = new Page<>(page, pageSize);
        if (totalRow != null) {
            searchPage.setTotalRow(totalRow);
        }
        QueryWrapper query = new QueryWrapper();
        query.select("*");
        return dictMapper.paginate(searchPage, query);
    }

    @Override
    public List<DictData> getDictDataList(Integer dictId) {

        return dictMapper.selectDictData(dictId);
    }

    @Override
    public Boolean addDict(DictType dictType, List<DictData> values) {
        int row = dictMapper.insert(dictType, true);
        if (row == 1) {
            log.info("新增字典主键Id:{}", dictType.getId());
            values.forEach((value) -> value.setTypeId(dictType.getId()));
            return dictMapper.insertDictValues(values) == values.size();
        }
        return false;
    }

    @Override
    public Boolean deleteDicts(List<Integer> dictIds) {
        int row = dictMapper.deleteBatchByIds(dictIds);
        return row == dictIds.size();
    }

    @Override
    public Boolean updateDict(DictInfo dictInfo) {
        boolean isUpdate = false;
        DictType dictType = new DictType();
        BeanUtils.copyProperties(dictInfo, dictType);
        if (BeanUtil.isEmpty(dictType)) {
            log.info("字典类型无需修改");
        } else {
            isUpdate = dictMapper.update(dictType) == 1;
        }
        // 更新键值列表
        if (dictInfo.getDeleteValues() != null && !dictInfo.getDeleteValues().isEmpty()) {
            isUpdate = dictMapper.deleteDictValues(dictInfo.getDeleteValues()) == dictInfo.getDeleteValues().size();
        }

        if (dictInfo.getAddValues() != null && !dictInfo.getAddValues().isEmpty()) {
            isUpdate = dictMapper.insertDictValues(dictInfo.getAddValues()) == dictInfo.getAddValues().size();
        }

        return isUpdate;
    }

    @Override
    public Page<RoleInfo> getRoles(Integer page, Integer pageSize,
                                   Integer totalRow) {
        Page<RoleInfo> searchPage = new Page<>(page, pageSize);
        if (totalRow != null) {
            searchPage.setTotalRow(totalRow);
        }
        QueryWrapper query = new QueryWrapper();
        query.select("*");
        roleMapper.paginateAs(searchPage, query, RoleInfo.class);
        searchPage.getRecords().forEach((r) -> {
            // 获取角色的菜单
            List<Integer> menus = menuMapper.selectRoleMenus(r.getId());
            r.setMenus(menus);
        });
        return searchPage;
    }

    @Override
    public Boolean addRole(Role role) {
        return roleMapper.insert(role, true) == 1;
    }

    @Override
    public Boolean deleteRoles(List<Integer> roleIds) {
        return roleMapper.deleteBatchByIds(roleIds) == roleIds.size();
    }

    @Override
    public List<BaseMenu> getAllMenu() {
        // 原始菜单列表
        List<BaseMenu> menus = menuMapper.selectAllBaseMenu();
        return Tools.generateMenus(menus);
    }

    @Override
    public Boolean updateRole(Role role, Integer roleId) {
        QueryWrapper query = new QueryWrapper();
        query.eq("id", roleId);
        if(roleMapper.updateByQuery(role, query) != 1){
            throw new ServiceException("更新角色失败");
        }
        return true;
    }

    @Override
    public Boolean updateRoleMenus(Integer roleId, HashMap<String, List<Integer>> menulist) {
        List<Integer> addMenus = menulist.get("addMenus");
        List<Integer> removeMenus = menulist.get("removeMenus");

        if (addMenus != null && !addMenus.isEmpty()) {
            if (addMenus.size() != roleMapper.addRoleMenus(roleId, addMenus)) {
                throw new ServiceException("角色菜单添加失败");
            }
        }
        if (removeMenus != null && !removeMenus.isEmpty()) {
            if (removeMenus.size() != roleMapper.removeRoleMenus(roleId, removeMenus)) {
                throw new ServiceException("角色菜单删除失败");
            }
        }
        return true;
    }
}

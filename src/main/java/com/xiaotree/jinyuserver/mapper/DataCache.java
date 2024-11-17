package com.xiaotree.jinyuserver.mapper;

import com.mybatisflex.core.query.QueryWrapper;
import com.xiaotree.jinyuserver.domain.entity.table.RoleTableDef;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataCache {
    private final RoleMapper roleMapper;

    public DataCache(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Cacheable(cacheNames = "perms")
    public List<String> getPerms(List<Integer> roleIds){
        return roleMapper.selectPermsByRoleIds(roleIds);
    }

    @Cacheable(cacheNames = "roles")
    public List<String> getRoles(List<Integer> roleIds){
        QueryWrapper query=new QueryWrapper();
        RoleTableDef r=RoleTableDef.ROLE;
        query.select(r.KEY).where(r.ID.in(roleIds));
        return roleMapper.selectListByQueryAs(query,String.class);
    }
}

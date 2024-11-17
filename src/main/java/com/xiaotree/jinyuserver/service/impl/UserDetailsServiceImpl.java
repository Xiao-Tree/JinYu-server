package com.xiaotree.jinyuserver.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.xiaotree.jinyuserver.domain.dto.LoginUser;
import com.xiaotree.jinyuserver.domain.entity.User;
import com.xiaotree.jinyuserver.domain.entity.table.UserTableDef;
import com.xiaotree.jinyuserver.domain.vo.BaseRole;
import com.xiaotree.jinyuserver.mapper.DataCache;
import com.xiaotree.jinyuserver.mapper.RoleMapper;
import com.xiaotree.jinyuserver.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final DataCache dataCache;

    public UserDetailsServiceImpl(UserMapper userMapper, RoleMapper roleMapper, DataCache dataCache) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.dataCache = dataCache;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("开始验证：UserDetailsService.loadUserByUsername");
        QueryWrapper query = new QueryWrapper();
        UserTableDef u = UserTableDef.USER;
        query.select(u.ID, u.PASSWORD, u.AVATAR).where(u.USERNAME.eq(username));
        User user = userMapper.selectOneByQuery(query);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        List<BaseRole> roles = roleMapper.getRolesByUserId(user.getId());
        List<Integer> roleIds = roles.stream().map(BaseRole::getId).toList();
        List<String> perms = dataCache.getPerms(roleIds);

        return LoginUser.builder()
                .id(user.getId())
                .username(username)
                .password(user.getPassword())
                .avatar(user.getAvatar())
                .roles(roles,true)
                .authorities(perms)
                .build();
    }
}

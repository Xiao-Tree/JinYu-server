package com.xiaotree.jinyuserver.domain.dto;


import com.xiaotree.jinyuserver.domain.vo.BaseRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;


public class LoginUser implements UserDetails {
    private final String username;

    private String password;

    @Getter
    private UserDetailsInfo userInfo;

    private final List<GrantedAuthority> authorities;

    private final boolean accountNonExpired;

    private final boolean accountNonLocked;

    private final boolean credentialsNonExpired;

    private final boolean enabled;


    public LoginUser(String username, String password,UserDetailsInfo userDetails, List<GrantedAuthority> authorities) {
        this(username, password, userDetails, authorities, true, true, true, true);
    }

    public LoginUser(String username, String password, UserDetailsInfo userDetails, List<GrantedAuthority> authorities, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled) {
        this.username = username;
        this.password = password;
        this.userInfo = userDetails;
        this.authorities = authorities;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static final class UserBuilder {
        private String username;
        private String password;
        private UserDetailsInfo userInfo;
        private List<GrantedAuthority> authorities;

        private void buildUserDetailsInfo() {
            if (userInfo == null) {
                userInfo = new UserDetailsInfo();
            }
        }

        public UserBuilder id(Integer id) {
            buildUserDetailsInfo();
            this.userInfo.setId(id);
            return this;
        }

        public UserBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder avatar(String avatar){
            buildUserDetailsInfo();
            this.userInfo.setAvatar(avatar);
            return this;
        }

        public UserBuilder token(String token){
            buildUserDetailsInfo();
            this.userInfo.setToken(token);
            return this;
        }

        public UserBuilder roles(String... roles) {
            List<String> rs = Arrays.stream(roles).map(role ->
                    role.startsWith("ROLE_") ? role : "ROLE_" + role
            ).toList();
            this.authorities = AuthorityUtils.createAuthorityList(rs);
            return this;
        }

        public UserBuilder roles(List<String> roles) {
            roles = roles.stream().map(role ->
                    role.startsWith("ROLE_") ? role : "ROLE_" + role
            ).toList();
            this.authorities = AuthorityUtils.createAuthorityList(roles);
            return this;
        }

        public UserBuilder roles(List<BaseRole> roles,boolean isDetail) {
            buildUserDetailsInfo();
            this.userInfo.setRoles(roles);
            List<String> roleKeys = roles.stream().map(role ->
                    role.getKey().startsWith("ROLE_") ? role.getKey() : "ROLE_" + role.getKey()
            ).toList();
            this.authorities = AuthorityUtils.createAuthorityList(roleKeys);
            return this;
        }

        public UserBuilder authorities(String... authorities) {
            List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(authorities);
            if (!this.authorities.isEmpty()) {
                this.authorities.addAll(authorityList);
            } else {
                this.authorities = authorityList;
            }
            return this;
        }

        public UserBuilder authorities(List<String> authorities) {
            List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(authorities);
            if (!this.authorities.isEmpty()) {
                this.authorities.addAll(authorityList);
            } else {
                this.authorities = authorityList;
            }
            return this;
        }

        public LoginUser build() {
            return new LoginUser( this.username, this.password, this.userInfo, this.authorities);
        }
    }
}
package com.xiaotree.jinyuserver.handler;

import com.xiaotree.jinyuserver.domain.dto.LoginUser;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import javax.security.auth.Subject;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private final LoginUser loginUser;

    public JwtAuthenticationToken(LoginUser loginUser, boolean isAuthenticated) {
        super(loginUser.getAuthorities());
        super.setAuthenticated(isAuthenticated);
        this.loginUser = loginUser;
    }

    public static JwtAuthenticationToken authenticated(LoginUser loginUser) {
        return new JwtAuthenticationToken(loginUser, true);
    }

    public static JwtAuthenticationToken unauthenticated(LoginUser loginUser) {
        return new JwtAuthenticationToken(loginUser, false);
    }

    @Override
    public Object getCredentials() {
        return this.loginUser.getUserInfo().getToken();
    }

    @Override
    public Object getPrincipal() {
        return this.loginUser;
    }

    @Override
    public boolean implies(Subject subject) {
        return super.implies(subject);
    }

}

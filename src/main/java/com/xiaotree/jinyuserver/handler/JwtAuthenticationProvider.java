package com.xiaotree.jinyuserver.handler;

import com.xiaotree.jinyuserver.domain.dto.LoginUser;
import com.xiaotree.jinyuserver.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@Slf4j
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final TokenUtil tokenUtil;

    public JwtAuthenticationProvider(TokenUtil tokenUtil) {
        this.tokenUtil = tokenUtil;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("开始认证：JwtAuthenticationProvider");
        if (authentication instanceof JwtAuthenticationToken auth) {
            log.info("捕获到 {}",auth.getClass().getSimpleName());
            String token= (String) auth.getCredentials();
            LoginUser user = tokenUtil.verifyToken(token);
            return JwtAuthenticationToken.authenticated(user);
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        boolean isSupport = JwtAuthenticationToken.class.isAssignableFrom(authentication);
        log.info("JwtAuthenticationProvider {} {}", isSupport ? "支持验证" : "不支持验证", authentication.getSimpleName());
        return isSupport;
    }
}

package com.xiaotree.jinyuserver.service.impl;

import com.xiaotree.jinyuserver.domain.Result;
import com.xiaotree.jinyuserver.domain.dto.LoginUser;
import com.xiaotree.jinyuserver.handler.JwtAuthenticationToken;
import com.xiaotree.jinyuserver.util.Tools;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AutoUserDetailsService implements RememberMeServices {
    @SneakyThrows
    @Override
    public Authentication autoLogin(HttpServletRequest request, HttpServletResponse response) {
        log.info("开始自动登录");
        //获取token
        String token = request.getHeader("Authorization");
        if (token == null) {
            if ("websocket".equals(request.getHeader("Upgrade"))) {
                log.info("拦截到websocket请求");
                String protocol = request.getHeader("Sec-websocket-protocol");
                if (protocol != null) {
                    token = protocol;
                } else {
                    log.error("没有从ws-header中获取到token");
                    return null;
                }
            } else {
                log.error("没有从header中获取到token");
                return null;
            }
        } else {
            token = token.substring("Bearer ".length());
        }
        return JwtAuthenticationToken.unauthenticated(LoginUser.builder().token(token).build());
    }

    @SneakyThrows
    @Override
    public void loginFail(HttpServletRequest request, HttpServletResponse response) {
        if(request.getHeader("Authorization")==null) {
            log.info("没有token,无法自动登录");
            return;
        }
        log.error("自动登录失败");
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        String json = Tools.objectToJson(new Result<>(HttpStatus.UNAUTHORIZED.value(), "凭证已失效，请重新登录！",null));
        response.getWriter().write(json);
    }

    @Override
    public void loginSuccess(HttpServletRequest request, HttpServletResponse response,
                             Authentication successfulAuthentication) {
        log.info("自动登录成功");
    }
}

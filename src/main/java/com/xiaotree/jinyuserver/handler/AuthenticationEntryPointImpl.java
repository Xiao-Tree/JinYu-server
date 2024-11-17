package com.xiaotree.jinyuserver.handler;

import com.xiaotree.jinyuserver.domain.Result;
import com.xiaotree.jinyuserver.util.Tools;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

@Slf4j
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        log.error("无效凭证，请重新登录");
        if (response.getContentType() != null) {
            log.info("请求已被处理");
            return;
        }
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        String json = Tools.objectToJson(new Result<>(HttpStatus.UNAUTHORIZED.value(), authException.getMessage(),null));
        response.getWriter().write(json);
    }
}

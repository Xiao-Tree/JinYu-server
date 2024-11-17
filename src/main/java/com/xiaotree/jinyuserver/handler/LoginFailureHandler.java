package com.xiaotree.jinyuserver.handler;

import com.xiaotree.jinyuserver.domain.Result;
import com.xiaotree.jinyuserver.util.Tools;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        String json = Tools.objectToJson(new Result<>(HttpStatus.UNAUTHORIZED.value(), exception.getMessage(), null));
        response.getWriter().write(json);
    }
}

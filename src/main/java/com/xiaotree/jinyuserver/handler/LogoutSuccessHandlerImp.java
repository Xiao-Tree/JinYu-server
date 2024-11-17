package com.xiaotree.jinyuserver.handler;

import com.xiaotree.jinyuserver.domain.Result;
import com.xiaotree.jinyuserver.util.Tools;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

public class LogoutSuccessHandlerImp implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(Tools.objectToJson(Result.success("登出成功", authentication.getPrincipal())));
    }
}

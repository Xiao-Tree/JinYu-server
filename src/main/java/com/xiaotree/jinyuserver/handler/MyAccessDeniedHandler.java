package com.xiaotree.jinyuserver.handler;

import com.xiaotree.jinyuserver.domain.Result;
import com.xiaotree.jinyuserver.util.Tools;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

import java.io.IOException;

public class MyAccessDeniedHandler extends AccessDeniedHandlerImpl {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException ex) throws IOException {
        if (response.isCommitted()) {
            logger.warn("响应已经提交");
            return;
        }
        logger.error("禁止访问，响应403状态码");
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        String json = Tools.objectToJson(new Result<>(HttpStatus.FORBIDDEN.value(), "你没有权限访问", ex.getMessage()));
        response.getWriter().write(json);
    }
}

package com.xiaotree.jinyuserver.handler;

import com.xiaotree.jinyuserver.domain.Result;
import com.xiaotree.jinyuserver.domain.dto.LoginUser;
import com.xiaotree.jinyuserver.domain.vo.BaseRole;
import com.xiaotree.jinyuserver.util.TokenUtil;
import com.xiaotree.jinyuserver.util.Tools;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class LoginSuccessHandlerImp implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        HashMap<String, Object> data = null;
        if (authentication.getPrincipal() instanceof LoginUser user) {
            data = new LinkedHashMap<>();
            data.put("uid", user.getUserInfo().getId());
            data.put("username", user.getUsername());
            data.put("avatar", user.getUserInfo().getAvatar());
            List<String> roles = user.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .filter(auth ->
                            auth.startsWith("ROLE_"))
                    .toList();
            data.put("roles", roles);
            data.put("auth", TokenUtil.createToken(user.getUserInfo().getId(),
                    user.getUserInfo().getRoles().stream().map(BaseRole::getId).toList()));
        }
        String json = Tools.objectToJson(Result.success("登陆成功", data));
        response.getWriter().write(json);
    }
}

package com.xiaotree.jinyuserver.controller;

import com.xiaotree.jinyuserver.domain.Result;
import com.xiaotree.jinyuserver.domain.vo.Dict;
import com.xiaotree.jinyuserver.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/auth")
    public Result<Authentication> auth(Authentication authentication) {
        return Result.success("当前认证信息", authentication);
    }

    @GetMapping(value = "/dicts/{dictKey}")
    public Result<Dict> getDict(@PathVariable String dictKey) {
        return Result.success(userService.getDictData(dictKey));
    }

    /**
     * 测试连接，并刷新session的缓存时间
     * @return
     */
    @GetMapping("/ping")
    public Result<Boolean> ping(){
        return Result.success("连接成功",true);
    }
}

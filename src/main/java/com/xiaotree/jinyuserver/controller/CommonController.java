package com.xiaotree.jinyuserver.controller;

import cn.hutool.system.SystemUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.xiaotree.jinyuserver.domain.Result;
import com.xiaotree.jinyuserver.handler.JwtAuthenticationToken;
import com.xiaotree.jinyuserver.service.UserService;
import com.xiaotree.jinyuserver.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {
    private final UserService userService;
    private final RedisUtil redisUtil;

    public CommonController(UserService userService, RedisUtil redisUtil) {
        this.userService = userService;
        this.redisUtil = redisUtil;
    }

    @GetMapping()
    public Result<String> home() {
        return Result.success("主页");
    }

    @PostMapping("/signup")
    public Result<String> signup() {
        return Result.success("注册");
    }

    @GetMapping("/test")
    public Result<Object> test() {
        HashMap<String, Object> loginUsers= redisUtil.getAllLoginUser();
//        if(!loginUsers.isEmpty()){
//            loginUsers.get((String) loginUsers.keySet().toArray()[0]).forEach(m->{
//                log.info("value -> {}",m.getClass().getSimpleName());
//            });
//        }
        return Result.success(loginUsers);
    }

    @GetMapping(value = "/username")
    public Result<Boolean> checkUsername(@RequestParam String username) {
        QueryWrapper query = new QueryWrapper();
        query.eq("username", username);
        Boolean isExist= userService.getOne(query) != null;
        return Result.success(isExist ? "用户名已存在" : "合法用户名", isExist);
    }
}

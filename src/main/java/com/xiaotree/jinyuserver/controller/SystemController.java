package com.xiaotree.jinyuserver.controller;

import cn.hutool.core.bean.BeanUtil;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.xiaotree.jinyuserver.domain.Result;
import com.xiaotree.jinyuserver.domain.dto.CreateUser;
import com.xiaotree.jinyuserver.domain.dto.UpdateUser;
import com.xiaotree.jinyuserver.domain.entity.DictData;
import com.xiaotree.jinyuserver.domain.entity.DictType;
import com.xiaotree.jinyuserver.domain.entity.Role;
import com.xiaotree.jinyuserver.domain.entity.User;
import com.xiaotree.jinyuserver.domain.entity.table.UserTableDef;
import com.xiaotree.jinyuserver.domain.vo.*;
import com.xiaotree.jinyuserver.service.SystemService;
import com.xiaotree.jinyuserver.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Slf4j
@RestController
@RequestMapping(value = "/system")
public class SystemController {
    private final UserService userService;
    private final SystemService systemService;

    public SystemController(UserService userService, SystemService systemService) {
        this.userService = userService;
        this.systemService = systemService;
    }

    @GetMapping("/auth")
    public Result<Authentication> auth(Authentication authentication) {
        return Result.success("当前认证信息", authentication);
    }

    @GetMapping(value = "/cache/roles")
    public ResponseEntity<Result<List<BaseRole>>> queryRoles() {
        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(1, TimeUnit.DAYS)
                .cachePrivate()).body(Result.success(userService.getAllRole()));
    }

    @GetMapping(value = "/cache/depts")
    public ResponseEntity<Result<List<DeptInfo>>> queryDepts() {
        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(1, TimeUnit.DAYS)
                .cachePrivate()).body(Result.success(userService.getAllDept()));
    }

    @PostMapping("/users")
    public Result<Boolean> createUser(@ModelAttribute CreateUser createUser) {
        userService.setUserInfo(createUser);
        userService.setUserRoles(createUser.getUsername(), createUser.getRoleIds());
        return Result.success("新增用户成功", Boolean.TRUE);
    }

    @DeleteMapping("/users")
    public Result<Boolean> removeUsers(@RequestParam List<Integer> userIds) {
        if (!userService.deleteAvatar(userIds)) {
            return Result.error("无法删除头像", false);
        }
        if (userService.removeByIds(userIds)) {
            return Result.success("成功删除用户", true);
        } else {
            return Result.error("无法删除用户", false);
        }
    }

    @GetMapping("/users")
    public Result<Page<UserInfo>> queryUsers(@RequestParam Integer page, @RequestParam Integer pageSize,
                                             @RequestParam(required = false) Integer totalRow) {
        BaseMapper<User> mapper = userService.getMapper();
        Page<UserInfo> searchPage = new Page<>(page, pageSize);
        if (totalRow != null) {
            searchPage.setTotalRow(totalRow);
        }
        QueryWrapper query = new QueryWrapper();
        UserTableDef u = UserTableDef.USER;
        query.select(u.ID, u.USERNAME, u.SEX, u.DEPT_ID, u.PHONE_NUM, u.AVATAR, u.EMAIL, u.STATUS, u.CREATE_AT);
        Page<UserInfo> userInfoPage = mapper.paginateAs(searchPage, query, UserInfo.class);
        List<UserInfo> records = userInfoPage.getRecords();
        records.forEach((userinfo) -> {
            List<Integer> roleIds = userService.selectRoleIdsById(userinfo.getId());
            userinfo.setRoleIds(roleIds);
        });
        return Result.success(userInfoPage);
    }

    @PutMapping("/users/{uid}")
    public Result<Boolean> updateUser(@ModelAttribute UpdateUser user, @PathVariable Integer uid) {
        User newUser = new User();
        BeanUtils.copyProperties(user, newUser, "id");
        // 更新头像
        newUser.setAvatar(userService.updateAvatar(user.getOriginalAvatar(), user.getAvatar()));
        boolean isUpdated = false;
        if (!BeanUtil.isEmpty(newUser)) {
            QueryWrapper query = new QueryWrapper();
            query.eq("id", uid);
            isUpdated = userService.update(newUser, query);
            if (isUpdated) {
                log.info("更新信息成功");
            } else {
                log.error("更新信息失败");
            }
        }
        // 更新角色列表
        isUpdated = userService.updateRoleIds(uid, user.getOriginalRoleIds(), user.getRoleIds()) || isUpdated;
        isUpdated = userService.updateUserId(uid, user.getId()) || isUpdated;
        return Result.success(isUpdated);
    }

    @GetMapping("/dicts")
    public Result<Page<DictType>> queryDicts(@RequestParam Integer page, @RequestParam Integer pageSize,
                                             @RequestParam(required = false) Integer totalRow) {
        return Result.success(systemService.getDictTypeList(page, pageSize, totalRow));
    }

    @GetMapping("/dicts/{dictId}")
    public Result<List<DictData>> queryDictData(@PathVariable Integer dictId) {
        return Result.success(systemService.getDictDataList(dictId));
    }

    @PostMapping("/dicts")
    public Result<Boolean> createDict(@RequestBody DictInfo dict) {
        DictType dictType = new DictType();
        BeanUtils.copyProperties(dict, dictType);

        if (systemService.addDict(dictType, dict.getValues())) {
            return Result.success(true);
        } else {
            return Result.error(false);
        }
    }

    @DeleteMapping("/dicts")
    public Result<Boolean> removeDicts(@RequestBody List<Integer> dictIds) {
        if (systemService.deleteDicts(dictIds)) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @PutMapping("/dicts")
    public Result<Boolean> updateDict(@RequestBody DictInfo dictInfo) {
        if (systemService.updateDict(dictInfo)) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @GetMapping("/roles")
    public Result<Page<RoleInfo>> queryRoles(@RequestParam Integer page, @RequestParam Integer pageSize,
                                             @RequestParam(required = false) Integer totalRow) {
        return Result.success(systemService.getRoles(page, pageSize, totalRow));
    }

    @DeleteMapping("/roles")
    public Result<Boolean> removeRoles(@RequestBody List<Integer> roleIds) {
        if (systemService.deleteRoles(roleIds)) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @PostMapping("/roles")
    public Result<Boolean> createRole(@ModelAttribute Role role) {
        if (systemService.addRole(role)) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @GetMapping("/roles/menu")
    public Result<List<BaseMenu>> queryBaseMenus() {
        return Result.success(systemService.getAllMenu());
    }

    @PutMapping("/roles/{roleId}")
    public Result<Boolean> updateRole(@ModelAttribute Role role, @PathVariable Integer roleId) {
        return Result.success(systemService.updateRole(role,roleId));
    }

    @PutMapping("/menus/{roleId}")
    public Result<Boolean> updateRoleMenus(@PathVariable Integer roleId, @RequestBody HashMap<String,List<Integer>> menulist){
        return Result.success(systemService.updateRoleMenus(roleId,menulist));
    }
}

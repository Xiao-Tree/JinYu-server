package com.xiaotree.jinyuserver.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xiaotree.jinyuserver.domain.dto.CreateUser;
import com.xiaotree.jinyuserver.domain.entity.DictType;
import com.xiaotree.jinyuserver.domain.entity.User;
import com.xiaotree.jinyuserver.domain.entity.table.UserTableDef;
import com.xiaotree.jinyuserver.domain.vo.DeptInfo;
import com.xiaotree.jinyuserver.domain.vo.Dict;
import com.xiaotree.jinyuserver.domain.vo.BaseRole;
import com.xiaotree.jinyuserver.handler.ServiceException;
import com.xiaotree.jinyuserver.mapper.DeptMapper;
import com.xiaotree.jinyuserver.mapper.DictMapper;
import com.xiaotree.jinyuserver.mapper.RoleMapper;
import com.xiaotree.jinyuserver.mapper.UserMapper;
import com.xiaotree.jinyuserver.service.UserService;
import com.xiaotree.jinyuserver.util.Tools;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Transactional
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private UserMapper userMapper;
    private RoleMapper roleMapper;
    private DictMapper dictMapper;
    private DeptMapper deptMapper;

    @Setter
    @Value("${file.upload}")
    private String uploadDir;

    public UserServiceImpl(RoleMapper roleMapper, DictMapper dictMapper, DeptMapper deptMapper, UserMapper userMapper) {
        this.roleMapper = roleMapper;
        this.dictMapper = dictMapper;
        this.deptMapper = deptMapper;
        this.userMapper = userMapper;
    }

    @Cacheable("dict")
    @Override
    public Dict getDictData(String dictKey) {
        QueryWrapper query = new QueryWrapper();
        query.select().eq("`key`", dictKey);
        DictType data = dictMapper.selectOneByQuery(query);
        return new Dict(data, dictMapper.selectSimpleDictData(data.getId()));
    }

    @Override
    public List<BaseRole> getAllRole() {
        QueryWrapper query = new QueryWrapper();
        query.select("id", "name");
        return roleMapper.selectListByQueryAs(query, BaseRole.class);
    }

    @Override
    public List<DeptInfo> getAllDept() {
        QueryWrapper query = new QueryWrapper();
        query.select("id", "name").eq("parent_id", 2).orderBy("order_num");
        return deptMapper.selectListByQueryAs(query, DeptInfo.class);
    }

    @Override
    public Boolean setUserInfo(CreateUser user) {
        int rowNum = -1;
        User newUser = new User();
        user.setPassword(Tools.encode(user.getPassword()));
        BeanUtils.copyProperties(user, newUser);
        if (user.getAvatar() != null && !user.getAvatar().isEmpty()) {
            String newFilename = Tools.getFileName(user.getAvatar().getOriginalFilename());
            newUser.setAvatar(newFilename);
            rowNum = userMapper.addUser(newUser);
            try {
                // 判断上传目录是否存在
                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    if (directory.mkdirs()) {
                        log.info("目录已创建: {}", uploadDir);
                    } else {
                        log.error("目录创建失败: {}", uploadDir);
                    }
                }
                // 保存文件
                if (rowNum == 1) {
                    user.getAvatar().transferTo(new File(uploadDir + newFilename));
                }

            } catch (IOException e) {
                String msg = "上传文件异常：" + e.getMessage();
                log.error(msg);
                throw new ServiceException(msg);
            }
        } else {
            rowNum = userMapper.addUser(newUser);
        }

        if (rowNum != 1) {
            throw new ServiceException("创建用户失败");
        }

        return true;
    }

    @Override
    public Boolean setUserRoles(String username, List<Integer> roleIds) {
        QueryWrapper query = new QueryWrapper();
        query.select("id").eq("username", username);
        User user = this.getOne(query);
        return userMapper.addRoleIds(user.getId(), roleIds) == roleIds.size();
    }

    @Override
    public List<Integer> selectRoleIdsById(Integer userId) {
        return roleMapper.selectUserRoleIds(userId);
    }


    @Override
    public boolean updateRoleIds(Integer userId, List<Integer> currentRoleIds, List<Integer> newRoleIds) {
        if (currentRoleIds == null || newRoleIds == null) {
            return false;
        }
        List<Integer> toRemove = currentRoleIds.stream()
                .filter((id) -> !newRoleIds.contains(id))
                .toList();
        List<Integer> toAdd = newRoleIds.stream()
                .filter((id) -> !currentRoleIds.contains(id))
                .toList();

        if (!toAdd.isEmpty()) {
            if (roleMapper.addRoleIds(userId, toAdd) != toAdd.size()) {
                throw new ServiceException("更新：添加角色失败");
            }
        }
        if (!toRemove.isEmpty()) {
            if (roleMapper.removeRoleIds(userId, toRemove) != toRemove.size()) {
                throw new ServiceException("更新：删除角色失败");
            }
        }

        return true;
    }

    @Override
    public boolean updateUserId(Integer originalId, Integer newId) {
        if (newId == null || originalId == null) {
            return false;
        } else {
            return userMapper.updateUserId(originalId, newId) == 1;
        }
    }

    @Override
    public String updateAvatar(String originalAvatar, MultipartFile newAvatar) {
        if (newAvatar == null || newAvatar.isEmpty()) {
            return null;
        }
        File oAvatar = new File(uploadDir + originalAvatar);
        boolean isDeleted = FileUtil.del(oAvatar);
        if (isDeleted) {
            log.info("已删除文件：{}", uploadDir + originalAvatar);
        } else {
            log.error("删除文件失败");
        }
        String newFileName = Tools.getFileName(newAvatar.getOriginalFilename());
        try {
            newAvatar.transferTo(new File(uploadDir + newFileName));
            log.info("更新头像成功");
        } catch (IOException e) {
            log.error("更新头像异常：{}", e.getMessage());
        }
        return newFileName;
    }

    @Override
    public boolean deleteAvatar(List<Integer> userIds) {
        UserTableDef u = UserTableDef.USER;
        QueryWrapper query = new QueryWrapper();
        query.select(u.AVATAR).where(u.ID.in(userIds));
        List<String> deleteAvatars = userMapper.selectListByQueryAs(query, String.class);
        try {
            deleteAvatars.forEach(a -> FileUtil.del(uploadDir + a));
        } catch (IORuntimeException e) {
            log.error("删除文件失败：{}", e.getMessage());
            return false;
        }
        return true;
    }
}

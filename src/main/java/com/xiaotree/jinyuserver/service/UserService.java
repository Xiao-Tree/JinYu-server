package com.xiaotree.jinyuserver.service;

import com.mybatisflex.core.service.IService;
import com.xiaotree.jinyuserver.domain.dto.CreateUser;
import com.xiaotree.jinyuserver.domain.entity.User;
import com.xiaotree.jinyuserver.domain.vo.DeptInfo;
import com.xiaotree.jinyuserver.domain.vo.Dict;
import com.xiaotree.jinyuserver.domain.vo.BaseRole;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService extends IService<User> {

    Dict getDictData(String dictKey);

    List<BaseRole> getAllRole();

    List<DeptInfo> getAllDept();

    Boolean setUserInfo(CreateUser user);

    Boolean setUserRoles(String username, List<Integer> roleIds);

    List<Integer> selectRoleIdsById(Integer userId);

    boolean updateRoleIds(Integer userId, List<Integer> currentRoleIds, List<Integer> newRoleIds);

    boolean updateUserId(Integer originalId, Integer newId);

    String updateAvatar(String originalAvatar, MultipartFile newAvatar);

    boolean deleteAvatar(List<Integer> userIds);
}

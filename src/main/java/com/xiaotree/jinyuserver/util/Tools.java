package com.xiaotree.jinyuserver.util;

import cn.hutool.core.lang.Snowflake;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaotree.jinyuserver.domain.vo.BaseMenu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class Tools {
    private static ObjectMapper mapper;
    private static PasswordEncoder passwordEncoder;
    private static Snowflake snowflake;

    public Tools(ObjectMapper mapper, PasswordEncoder passwordEncoder, Snowflake snowflake) {
        Tools.mapper = mapper;
        Tools.passwordEncoder = passwordEncoder;
        Tools.snowflake = snowflake;
    }

    public static String objectToJson(Object obj) {
        String json = null;
        try {
            json = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("{} 序列化json失败", obj.getClass().getSimpleName());
        }
        return json;
    }

    public static String encode(String password) {
        return passwordEncoder.encode(password);
    }

    public static String getFileName(String originalName) {
        // 获取当前时间
        LocalDate now = LocalDate.now();
        // 定义时间格式化的格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-");

        String dateStr = now.format(formatter);
        String extension = originalName.substring(originalName.lastIndexOf("."));
        return dateStr + snowflake.nextIdStr() + extension;
    }

    public static List<BaseMenu> generateMenus(List<BaseMenu> menus){
        // 生成 id->menu 字典结构
        Map<Integer, BaseMenu> menuMap = menus.stream().collect(Collectors.toMap(BaseMenu::getId, menu -> menu));
        // 嵌套菜单列表
        List<BaseMenu> menuList = new ArrayList<>();
        for (BaseMenu menu : menus) {
            Integer parentId = menu.getParentId();
            if (parentId == null) {
                menuList.add(menu);
            } else {
                BaseMenu parent = menuMap.get(parentId);
                if (parent.getChildren() == null) {
                    parent.setChildren(new ArrayList<>());
                }
                parent.getChildren().add(menu);
            }
        }
        return menuList;
    }
}

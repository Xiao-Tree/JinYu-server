package com.xiaotree.jinyuserver.config;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public Snowflake createSnowFlake() {
        return IdUtil.getSnowflake(1, 1);
    }
}

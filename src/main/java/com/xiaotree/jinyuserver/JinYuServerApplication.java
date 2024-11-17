package com.xiaotree.jinyuserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

@MapperScan("com.xiaotree.jinyuserver.mapper")
@EnableCaching
@SpringBootApplication(exclude = SpringDataWebAutoConfiguration.class)
public class JinYuServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(JinYuServerApplication.class, args);
    }

}

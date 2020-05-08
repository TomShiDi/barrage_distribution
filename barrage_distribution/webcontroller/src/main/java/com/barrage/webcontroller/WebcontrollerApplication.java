package com.barrage.webcontroller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.barrage.api","com.barrage.webcontroller"}) // 1. 多模块项目需要扫描的包
@EnableJpaRepositories("com.barrage.api.repository") // 2. Dao 层所在的包
@EntityScan("com.barrage.api.entity") // 3. Entity 所在的包
@EnableCaching
@EnableJpaAuditing
public class WebcontrollerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebcontrollerApplication.class, args);
    }

}

package com.example.familygoods;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 家庭物品管理系统 启动类
 */
@SpringBootApplication
@MapperScan("com.example.familygoods.mapper")
public class FamilyGoodsApplication {
    public static void main(String[] args) {
        SpringApplication.run(FamilyGoodsApplication.class, args);
        System.out.println("===== 家庭物品管理系统启动成功 =====");
    }
}

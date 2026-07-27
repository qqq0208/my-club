package com.md.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    private static final String AUTHOR = "CM";
    private static final String URL = "http://localhost:23101/index.html";
    private static final String TITLE = "my-club";
    private static final String INFO = "MyClub 管理系统是基于 SpringBoot 开发的，旨在提供一个全面而高效的管理平台，该系统目前仅支持内部员工登录，登陆后可以轻松管理俱乐部的房间，学校，资产，部门，员工，角色，菜单，班级，课程，学员等相关数据。该系统使用前后端分离的模式进行开发，数据库使用 MySQL，后端使用经典的 SSM 架构，前端使用 Vue + ElementPlus 的组合。该系统具有良好的可扩展性和稳定性，为俱乐部的管理和运营提供了可靠的支持。";
    private static final String VERSION = "1.0.0";

    /** 通用信息Bean */
    @Bean
    public OpenAPI commonInfo() {
        return new OpenAPI().info(new Info()
                .title(TITLE)
                .description(INFO)
                .version(VERSION)
                .contact(new Contact().name(AUTHOR).url(URL)));
    }
}
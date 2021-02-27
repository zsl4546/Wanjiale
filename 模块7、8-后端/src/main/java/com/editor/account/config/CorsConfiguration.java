package com.editor.account.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/1/3
 * Time: 20:27
 * Description: No Description
 */
@Configuration
public class CorsConfiguration extends WebMvcConfigurationSupport { //全局跨域配置
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") //拦截所有请求
                .allowedOrigins("http://localhost:8080", "http://localhost:8081") //允许的跨域域名
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(3600);
    }
}

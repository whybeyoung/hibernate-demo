/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: ApplicationConfig
 * Author:   xwliu
 * Date:     2018/3/30 17:01
 * Description: 应用配置
 */
package com.iflytek.iaas.config;

import com.iflytek.iaas.shiro.realm.IaasRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 〈主应用配置〉
 *
 * @author xwliu
 * @create 2018/3/30
 */
@Configurable
@Import(WebMvcConfig.class)
public class ApplicationConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilter(){

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        Map<String,String> filterChains = new LinkedHashMap<String,String>();
        filterChains.put("/api/v1/login","anon");
        filterChains.put("/api/v1/verify","anon");
        filterChains.put("/**","authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChains);
        return shiroFilterFactoryBean;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAdvisor(){
        AuthorizationAttributeSourceAdvisor authorizationAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAdvisor.setSecurityManager(securityManager());
        return authorizationAdvisor;
    }

    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(new IaasRealm());
        return securityManager;
    }
}

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
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@EnableConfigurationProperties(ImageHubConfig.class)
public class ApplicationConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String,String> filterChains = new LinkedHashMap<String,String>();
        filterChains.put("/api/v1/login","anon");
        filterChains.put("/api/v1/verify","anon");
        filterChains.put("/api/v1/logout","anon");
        filterChains.put("/api/v1/users/current","anon");
        //swagger allowed
        filterChains.put("/swagger-ui.html","anon");
        filterChains.put("/webjars/springfox-swagger-ui/**","anon");
        filterChains.put("/swagger-resources","anon");
        filterChains.put("/swagger-resources/**","anon");
        filterChains.put("/v2/api-docs","anon");

        filterChains.put("/**","authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChains);
        return shiroFilterFactoryBean;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAdvisor.setSecurityManager(securityManager);
        return authorizationAdvisor;
    }

    @Bean
    public IaasRealm iaasRealm() {
        return new IaasRealm();
    }

    @Bean
    public SecurityManager securityManager(IaasRealm iaasRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(iaasRealm);
        return securityManager;
    }
}

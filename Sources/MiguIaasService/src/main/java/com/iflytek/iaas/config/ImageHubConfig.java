package com.iflytek.iaas.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "docker")
public class ImageHubConfig {


    private String hub_api;
    private String hub_address;
    private String hub_user;
    private String hub_pwd;
    private String hub_tag;

    private String ssh_address;
    private Integer ssh_port;
    private String ssh_user;
    private String ssh_pwd;

    public String getHub_api() {
        return hub_api;
    }

    public void setHub_api(String hub_api) {
        this.hub_api = hub_api;
    }

    public String getHub_address() {
        return hub_address;
    }

    public void setHub_address(String hub_address) {
        this.hub_address = hub_address;
    }

    public String getHub_user() {
        return hub_user;
    }

    public void setHub_user(String hub_user) {
        this.hub_user = hub_user;
    }

    public String getHub_pwd() {
        return hub_pwd;
    }

    public void setHub_pwd(String hub_pwd) {
        this.hub_pwd = hub_pwd;
    }

    public String getHub_tag() {
        return hub_tag;
    }

    public void setHub_tag(String hub_tag) {
        this.hub_tag = hub_tag;
    }

    public String getSsh_address() {
        return ssh_address;
    }

    public void setSsh_address(String ssh_address) {
        this.ssh_address = ssh_address;
    }

    public Integer getSsh_port() {
        return ssh_port;
    }

    public void setSsh_port(Integer ssh_port) {
        this.ssh_port = ssh_port;
    }

    public String getSsh_user() {
        return ssh_user;
    }

    public void setSsh_user(String ssh_user) {
        this.ssh_user = ssh_user;
    }

    public String getSsh_pwd() {
        return ssh_pwd;
    }

    public void setSsh_pwd(String ssh_pwd) {
        this.ssh_pwd = ssh_pwd;
    }
}

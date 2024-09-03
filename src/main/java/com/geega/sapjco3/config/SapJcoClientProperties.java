package com.geega.sapjco3.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * SAP连接配置属性
 */
@ConfigurationProperties(prefix = "jco.client")
public class SapJcoClientProperties {

    /**
     * SAP主机IP
     */
    private String ashost;

    /**
     * SAP实例
     */
    private String sysnr;

    /**
     * 客户端
     */
    private String client;

    /**
     * 用户名
     */
    private String user;

    /**
     * 密码
     */
    private String passwd;

    /**
     * 登录语言
     */
    private String lang;

    /**
     * 连接名称
     */
    private String poolName;

    public String getAshost() {
        return ashost;
    }

    public void setAshost(String ashost) {
        this.ashost = ashost;
    }

    public String getSysnr() {
        return sysnr;
    }

    public void setSysnr(String sysnr) {
        this.sysnr = sysnr;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }
}
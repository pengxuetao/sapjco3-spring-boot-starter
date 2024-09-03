package com.geega.sapjco3.config;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.ext.DestinationDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * SapJco自动配置类
 */
@AutoConfiguration
@ConditionalOnProperty(prefix = "jco.client", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(value = SapJcoClientProperties.class)
public class SapJcoAutoConfig {

    @Autowired
    private SapJcoClientProperties clientProperties;

    /**
     * 配置SAP客户端
     * @return JCoDestination
     * @throws JCoException
     */
    @Bean
    public JCoDestination jCoDestination() throws JCoException {
        Properties connectProperties = new Properties();
        connectProperties.put(DestinationDataProvider.JCO_ASHOST, clientProperties.getAshost());
        connectProperties.put(DestinationDataProvider.JCO_SYSNR, clientProperties.getSysnr());
        connectProperties.put(DestinationDataProvider.JCO_CLIENT, clientProperties.getClient());
        connectProperties.put(DestinationDataProvider.JCO_USER, clientProperties.getUser());
        connectProperties.put(DestinationDataProvider.JCO_PASSWD, clientProperties.getPasswd());
        connectProperties.put(DestinationDataProvider.JCO_LANG, clientProperties.getLang());

        createRepositoryDestination(clientProperties.getPoolName(), connectProperties);
        return JCoDestinationManager.getDestination(clientProperties.getPoolName());
    }

    private void createRepositoryDestination(String destName, Properties connectProperties) {
        try {
            String destCfg = destName + ".jcoDestination";
            File destFile = new File(destCfg);
            destFile.deleteOnExit();

            OutputStream os = new FileOutputStream(destFile, false);
            connectProperties.store(os, "sap客户端配置");
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
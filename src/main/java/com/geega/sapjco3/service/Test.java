package com.geega.sapjco3.service;

import com.sap.conn.jco.*;
import com.sap.conn.jco.ext.DestinationDataProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Test {

    public static void main(String[] args) {
        try {
            Properties properties = new Properties();
            properties.setProperty(DestinationDataProvider.JCO_ASHOST, "172.16.30.64");
            properties.setProperty(DestinationDataProvider.JCO_SYSNR, "10");
            properties.setProperty(DestinationDataProvider.JCO_CLIENT, "EQ2");
            properties.setProperty(DestinationDataProvider.JCO_USER, "GYMD");
            properties.setProperty(DestinationDataProvider.JCO_PASSWD, "Sap123456");
            properties.setProperty(DestinationDataProvider.JCO_LANG, "ZH");

            createDestinationDataFile("sap", properties);

            JCoDestination jCoDestination = JCoDestinationManager.getDestination("sap");

            JCoRepository jCoRepository = jCoDestination.getRepository();
            JCoFunction jCoFunction = jCoRepository.getFunction("ZFUN_SAPWMS_GSXX");

            // 请求参数
            jCoFunction.getImportParameterList().setValue("ROWCOUNT", "1");

            // 调用RFC
            jCoFunction.execute(jCoDestination);

            // 响应参数
            String result = jCoFunction.getExportParameterList().getString("GSXX");

            // 表结构
            JCoTable gsxx = jCoFunction.getTableParameterList().getTable("GSXX");
            for (int i = 0; i < gsxx.getNumRows(); i++) {
                gsxx.setRow(i);
                String bukrs = gsxx.getString("BUKRS");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createDestinationDataFile(String dName, Properties properties) {
        try {
            String cfg = dName + ".jcoDestination";
            File destFile = new File(cfg);
            destFile.deleteOnExit();

            OutputStream out = new FileOutputStream(destFile, false);
            properties.store(out, "sap配置文件");
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

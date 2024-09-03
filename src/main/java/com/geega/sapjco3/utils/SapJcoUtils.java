package com.geega.sapjco3.utils;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;

/**
 * SapJco工具类
 */
public class SapJcoUtils {

    public static <T> void transferImportParameterList(JCoFunction jCoFunction, T t) {
        for (Field field : t.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            // 请求参数
            try {
                jCoFunction.getImportParameterList().setValue(field.getName().toUpperCase(), field.get(t));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public static <T> List<T> transferTableParameterList(JCoFunction jCoFunction, T t) {
        // 获取CgrktkDTO类的Class对象
        T clazz = null;
        // 表结构
        JCoTable table = jCoFunction.getTableParameterList().getTable("CGRKTK");
        for (int i = 0; i < table.getNumRows(); i++) {
            table.setRow(i);
            for (Field field : t.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                // 请求参数
                String value = table.getString(field.getName().toUpperCase());
                try {
                    jCoFunction.getImportParameterList().setValue(field.getName().toUpperCase(), field.get(t));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

}

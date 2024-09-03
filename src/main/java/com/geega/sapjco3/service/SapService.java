package com.geega.sapjco3.service;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SapService {

    @Autowired
    private JCoDestination jCoDestination;

    public List<Map<String, Object>> callSapFunction(String functionName,
                                                     Map<String, Object> importParams,
                                                     String tableName,
                                                     Map<String, Object> tables) {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            JCoFunction function = jCoDestination.getRepository().getFunction(functionName);
            // 设置参数、执行、处理输出
            if (!importParams.isEmpty()) {
                for (String param : importParams.keySet()) {
                    function.getImportParameterList().setValue(param, importParams.get(param));
                }
            }
            function.execute(jCoDestination);

            JCoTable table = function.getTableParameterList().getTable(tableName);
            for (int i = 0; i < table.getNumRows(); i++) {
                Map<String, Object> map = new HashMap<>();
                table.setRow(i);
                for (String column : tables.keySet()) {
                    map.put(column, table.getString(column));
                }
                list.add(map);
            }
        } catch (JCoException e) {
            throw new RuntimeException("SAP Call Failed", e);
        }
        return list;
    }
}

package com.sdpizza.groupproject.database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.*;

public class QueryResult {
    private String table;
    private String schema;
    private ArrayList<HashMap<String, Object>> results;
    private int cursor = -1;

    private QueryResult() {}

    public QueryResult(ResultSet resultSet) {
        results = new ArrayList<>();
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            schema = metaData.getSchemaName(1);
            table = metaData.getTableName(1);
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                HashMap<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; ++i) {
                    row.put(metaData.getColumnName(i).toLowerCase(),
                            resultSet.getObject(i));
                }

                results.add(row);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }

    public boolean nextRow() {
        return (++cursor <= (results.size() - 1));
    }

    public HashMap<String, Object> getRowWithColumns() {
        assert(cursor != -1);
        return results.get(cursor);
    }

    public Collection<Object> getRowValues() {
        assert(cursor != -1);
        return (results.get(cursor).values());
    }

    public String getTableName() {
        return table;
    }

    public String getSchemaName() {
        return schema;
    }

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        for (HashMap<String, Object> map : results) {
            strBuilder.append(map.toString());
        }

        return strBuilder.toString();
    }
}

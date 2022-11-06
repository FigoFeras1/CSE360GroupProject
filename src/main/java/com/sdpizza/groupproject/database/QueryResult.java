package com.sdpizza.groupproject.database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryResult {
    private List<HashMap<String, Object>> results;
    private int cursor = -1;

    private QueryResult() {}

    public QueryResult(ResultSet resultSet) {
        results = new ArrayList<>();
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                HashMap<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; ++i) {
                    row.put(metaData.getColumnName(i), resultSet.getObject(i));
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

    public Map<String, Object> getRowWithColumns() {
        assert(cursor != -1);
        return results.get(cursor);
    }

    public List<Object> getRowValues() {
        assert(cursor != -1);
        return ((List<Object>) results.get(cursor).values());
    }
}

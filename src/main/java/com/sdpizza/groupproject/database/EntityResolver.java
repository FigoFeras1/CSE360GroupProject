package com.sdpizza.groupproject.database;

import com.sdpizza.groupproject.database.annotations.Column;
import com.sdpizza.groupproject.entity.Entity;
import com.sdpizza.groupproject.entity.model.Model;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityResolver {
    private final Class<? extends Entity> clazz;

    public EntityResolver(Class<? extends Entity> clazz) {
        this.clazz = clazz;
    }

    /* Alternate Resolve? */
    public <E extends Entity> E resolve(QueryResult queryResult) {
        List<Field> fields =
                Arrays.stream(clazz.getDeclaredFields())
                        .filter(f -> f.isAnnotationPresent(Column.class))
                        .collect(Collectors.toList());
        for (Field field : fields) {
            System.out.println(field.getAnnotation(Column.class).value());
        }

        return null;
    }

    /* Never used yet deprecated... */
    @Deprecated
    public static Model resolve(ResultSet resultSet,
                                Class<? extends Model> clazz) {
        Model model;
        /* I'll do proper error handling one day.... */
        try {
            model = clazz.getDeclaredConstructor().newInstance();
            Field[] fields = model.getClass().getDeclaredFields();
            Field field;

            int columnCount = resultSet.getMetaData().getColumnCount();
            for(int i = 1; i <= fields.length; ++i) {
                field = fields[i - 1];
                field.setAccessible(true);

                if (field.getType().isEnum()) {
                    Object value = field
                                    .getType()
                                    .getMethod("valueOf", String.class)
                                    .invoke(null, resultSet.getString(i)
                                                               .toUpperCase());
                    field.set(model, value);
                    continue;
                }

                field.set(model, resultSet.getObject(i));
                ++i;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("[ERROR]: " + ex.getMessage());

            return null;
        }

        return model;
    }
}

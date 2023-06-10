package edu.school21.app.ORM;

import org.reflections.Reflections;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OrmManager {
    private Connection connection;
    private String tableName;

    public OrmManager(DataSource dataSource) {
        try {
            connection = dataSource.getConnection();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void createTable() {
        Reflections reflections = new Reflections("edu.school21.app.Model");
        Set<Class<?>> elements = reflections.getTypesAnnotatedWith(OrmEntity.class);

        for (Class<?> element : elements) {
            try {
                OrmEntity entity = element.getAnnotation(OrmEntity.class);
                this.tableName = entity.table();
                Statement statement = connection.createStatement();
                String dropQuery = String.format("DROP TABLE IF EXISTS %s CASCADE;", this.tableName);
                statement.execute(dropQuery);
                System.out.println(dropQuery);

                StringBuilder queryBuilder = new StringBuilder();
                queryBuilder.append(String.format("CREATE TABLE IF NOT EXISTS %s (", this.tableName));

                List<String> columnDefinitions = new ArrayList<>();

                Field[] fields = element.getDeclaredFields();
                for (Field field : fields) {
                    if (field.isAnnotationPresent(OrmColumnId.class)) {
                        columnDefinitions.add(String.format("%s SERIAL PRIMARY KEY", field.getName()));
                    } else if (field.isAnnotationPresent(OrmColumn.class)) {
                        OrmColumn column = field.getAnnotation(OrmColumn.class);
                        String columnDefinition = String.format("%s %s", column.name(), getColumnType(field));
                        columnDefinitions.add(columnDefinition);
                    }
                }

                queryBuilder.append(String.join(", ", columnDefinitions));
                queryBuilder.append(");");
                statement.execute(queryBuilder.toString());
                System.out.println(queryBuilder);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void save(Object entity) {
        Class<?> tmp = entity.getClass();
        Field[] fields = tmp.getDeclaredFields();
        if (!tmp.isAnnotationPresent(OrmEntity.class)) {
            System.err.println("Entity is not annotated!");
            return;
        }
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(String.format("INSERT INTO %s (", this.tableName));

        List<String> columnNames = new ArrayList<>();
        List<String> columnValues = new ArrayList<>();

        for (Field field : fields) {
            if (field.isAnnotationPresent(OrmColumn.class)) {
                OrmColumn column = field.getAnnotation(OrmColumn.class);
                columnNames.add(column.name());
                field.setAccessible(true);
                try {
                    Object value = field.get(entity);
                    columnValues.add(String.format("'%s'", value));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                field.setAccessible(false);
            }
        }

        queryBuilder.append(String.join(", ", columnNames));
        queryBuilder.append(") VALUES (");
        queryBuilder.append(String.join(", ", columnValues));
        queryBuilder.append(");");

        try {
            Statement statement = connection.createStatement();
            statement.execute(queryBuilder.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(queryBuilder);
    }

    public void update(Object entity) {
        Class<?> tmp = entity.getClass();
        Field[] fields = tmp.getDeclaredFields();
        if (!tmp.isAnnotationPresent(OrmEntity.class)) {
            System.err.println("Entity is not annotated!");
            return;
        }
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(String.format("UPDATE %s SET ", this.tableName));

        List<String> updateClauses = new ArrayList<>();
        String idColumnName = null;
        Object idValue = null;

        for (Field field : fields) {
            if (field.isAnnotationPresent(OrmColumn.class)) {
                OrmColumn column = field.getAnnotation(OrmColumn.class);
                String columnName = column.name();
                field.setAccessible(true);
                try {
                    Object value = field.get(entity);
                    updateClauses.add(String.format("%s = '%s'", columnName, value));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                field.setAccessible(false);
            } else if (field.isAnnotationPresent(OrmColumnId.class)) {
                OrmColumnId columnId = field.getAnnotation(OrmColumnId.class);
                String columnName = columnId.name();
                field.setAccessible(true);
                try {
                    Object value = field.get(entity);
                    idColumnName = columnName;
                    idValue = value;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                field.setAccessible(false);
            }
        }

        queryBuilder.append(String.join(", ", updateClauses));
        queryBuilder.append(String.format(" WHERE %s = '%s';", idColumnName, idValue));

        if (idValue == null) {
            System.err.println("Cannot update entity without a valid identifier!");
            return;
        }

        try {
            Statement statement = connection.createStatement();
            statement.execute(queryBuilder.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(queryBuilder);
    }


    public <T> T findById(Long id, Class<T> clazz) {
        if (!clazz.isAnnotationPresent(OrmEntity.class)) {
            return null;
        }
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(String.format("SELECT * FROM %s WHERE id = %d", this.tableName, id));
        T object = null;
        try {
            object = clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException |
                 NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(queryBuilder.toString());
            System.out.println(queryBuilder);
            if (!resultSet.next()) {
                System.err.println("No such entity!");
                return null;
            }
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(OrmColumnId.class)) {
                    field.set(object, resultSet.getInt(1));
                } else if (field.isAnnotationPresent(OrmColumn.class)) {
                    OrmColumn column = field.getAnnotation(OrmColumn.class);
                    field.set(object, resultSet.getObject(column.name()));
                }
            }
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return object;
    }

    private String getColumnType(Field field) {
        OrmColumn column = field.getAnnotation(OrmColumn.class);
        if (column == null) {
            return "";
        }

        String fieldType = field.getType().getSimpleName();
        if (fieldType.equals("String")) {
            return String.format("VARCHAR(%d)", column.length());
        } else if (fieldType.equals("Integer")) {
            return "INTEGER";
        } else if (fieldType.equals("Long")) {
            return "BIGINT";
        } else if (fieldType.equals("Boolean")) {
            return "BOOLEAN";
        }

        return "";
    }
}

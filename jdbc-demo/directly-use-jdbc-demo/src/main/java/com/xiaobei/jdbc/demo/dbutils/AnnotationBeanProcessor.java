package com.xiaobei.jdbc.demo.dbutils;

import com.xiaobei.jdbc.demo.domain.User;
import org.apache.commons.dbutils.BeanProcessor;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/10 14:23
 */
public class AnnotationBeanProcessor extends BeanProcessor {
    /**
     * ResultSet column to bean property name overrides.
     */
    private Map<String, String> columnToPropertyOverrides;

    @Override
    public <T> T toBean(ResultSet rs, Class<? extends T> type) throws SQLException {
        columnToPropertyByAnnotations(type);
        return super.toBean(rs, type);
    }

    @Override
    public <T> List<T> toBeanList(ResultSet rs, Class<? extends T> type) throws SQLException {
        columnToPropertyByAnnotations(type);
        return super.toBeanList(rs, type);
    }

    @Override
    protected int[] mapColumnsToProperties(ResultSetMetaData rsmd, PropertyDescriptor[] props) throws SQLException {

        int cols = rsmd.getColumnCount();
        int[] columnToProperty = new int[cols + 1];
        Arrays.fill(columnToProperty, PROPERTY_NOT_FOUND);
        for (int col = 1; col <= cols; col++) {
            String columnName = rsmd.getColumnLabel(col);
            if (null == columnName || 0 == columnName.length()) {
                columnName = rsmd.getColumnName(col);
            }
            String propertyName = null;
            if(columnToPropertyOverrides != null) {
                propertyName = columnToPropertyOverrides.get(columnName);
            }
            if (propertyName == null) {
                propertyName = columnName;
            }
            for (int i = 0; i < props.length; i++) {
                if (propertyName.equalsIgnoreCase(props[i].getName())) {
                    columnToProperty[col] = i;
                    break;
                }
            }
        }

        return columnToProperty;
    }

    /**
     * TODO
     * @return
     */
    private <T> void columnToPropertyByAnnotations(Class<? extends T> type) {
        Field[] declaredFields = User.class.getDeclaredFields();
        for (Field field : declaredFields) {
            ColumnName annotation = field.getAnnotation(ColumnName.class);
            if(annotation != null) {
                if(columnToPropertyOverrides == null) {
                    columnToPropertyOverrides = new HashMap<>(declaredFields.length);
                }
                columnToPropertyOverrides.put(annotation.value(), field.getName());
            }
        }
    }
}

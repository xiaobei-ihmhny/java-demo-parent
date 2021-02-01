package com.xiaobei.jdbc.demo.dbutils;

import org.apache.commons.dbutils.BeanProcessor;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * 通过注解的方式来获取column与property的对应关系
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-02-01 20:29:29
 */
public class AnnotationBeanProcessor2 extends BeanProcessor {

    @Override
    protected int[] mapColumnsToProperties(ResultSetMetaData rsmd, PropertyDescriptor[] props) throws SQLException {
        int[] columnToProperty = super.mapColumnsToProperties(rsmd, props);
        int cols = rsmd.getColumnCount();
        for (int col = 1; col <= cols; col++) {
            if(columnToProperty[col] == PROPERTY_NOT_FOUND) {
                // 说明当前位置尚未匹配到
                String columnName = rsmd.getColumnLabel(col);
                if (null == columnName || 0 == columnName.length()) {
                    columnName = rsmd.getColumnName(col);
                }
                for (int i = 0; i < props.length; i++) {
                    Method method = props[i].getWriteMethod();
                    ColumnName colName = method.getAnnotation(ColumnName.class);
                    if(colName != null && columnName.equals(colName.value())) {
                        columnToProperty[col] = i;
                        break;
                    }
                }
            }
        }
        return columnToProperty;
    }
}

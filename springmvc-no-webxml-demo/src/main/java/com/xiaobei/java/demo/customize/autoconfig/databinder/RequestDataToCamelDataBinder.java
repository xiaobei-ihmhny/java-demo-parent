package com.xiaobei.java.demo.customize.autoconfig.databinder;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

import javax.servlet.ServletRequest;
import java.lang.reflect.Field;

/**
 * 请求、响应参数绑定
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-01-19 10:12:12
 */
public class RequestDataToCamelDataBinder extends ExtendedServletRequestDataBinder {

    public RequestDataToCamelDataBinder(Object target) {
        super(target);
    }

    public RequestDataToCamelDataBinder(Object target, String objectName) {
        super(target, objectName);
    }

    @Override
    protected void addBindValues(MutablePropertyValues mpvs,
                                 ServletRequest request) {
        super.addBindValues(mpvs, request);
        System.out.println("进行绑定方法.....");
        //处理参数绑定
        Class<?> targetClass = getTarget().getClass();
        // 获取所有的属性
        Field[] fields = targetClass.getDeclaredFields();
        for (Field field : fields) {
            BindParam bindParam = field.getAnnotation(BindParam.class);
            if(bindParam != null && mpvs.contains(bindParam.name())) {
                if(!mpvs.contains(field.getName())) {
                    mpvs.add(field.getName(), mpvs.getPropertyValue(bindParam.name()).getValue());
                }
            }
        }
    }
}

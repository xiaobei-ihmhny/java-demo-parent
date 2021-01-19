package com.xiaobei.java.demo.customize.autoconfig.databinder;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

/**
 * TODO
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-01-19 10:27:27
 */
public class AttributeMethodProcessor extends ServletModelAttributeMethodProcessor
        implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public AttributeMethodProcessor(boolean annotationNotRequired) {
        super(annotationNotRequired);
    }

    @Override
    protected void bindRequestParameters(WebDataBinder binder, NativeWebRequest request) {
        RequestDataToCamelDataBinder dataBinder =
                new RequestDataToCamelDataBinder(binder.getTarget(), binder.getObjectName());
        RequestMappingHandlerAdapter handlerAdapter =
                applicationContext.getBean(RequestMappingHandlerAdapter.class);
        handlerAdapter.getWebBindingInitializer().initBinder(dataBinder, request);
        super.bindRequestParameters(dataBinder, request);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

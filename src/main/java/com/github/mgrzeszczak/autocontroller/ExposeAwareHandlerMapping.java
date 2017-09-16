package com.github.mgrzeszczak.autocontroller;

import com.google.common.base.CaseFormat;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.condition.ConsumesRequestCondition;
import org.springframework.web.servlet.mvc.condition.HeadersRequestCondition;
import org.springframework.web.servlet.mvc.condition.ParamsRequestCondition;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.ProducesRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

class ExposeAwareHandlerMapping extends RequestMappingHandlerMapping {

    public ExposeAwareHandlerMapping() {
        super();
        setOrder(0);
    }

    @Override
    protected void initHandlerMethods() {
        super.initHandlerMethods();
    }

    @Override
    protected void detectHandlerMethods(Object handler) {
        super.detectHandlerMethods(handler);
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        getApplicationContext()
                .getBeansOfType(Object.class)
                .values()
                .forEach(this::scanBean);
    }

    @Override
    protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {
        super.registerHandlerMethod(handler, method, mapping);
    }

    private void scanBean(Object bean) {
        ReflectionUtils.doWithMethods(bean.getClass(), m -> registerExposedMethod(m, bean), this::filter);
    }

    private boolean filter(Method method) {
        return method.isAnnotationPresent(Expose.class);
    }

    private void registerExposedMethod(Method method, Object bean) {
        registerHandlerMethod(bean, method, createRequestMapping(method, bean));
    }

    private RequestMappingInfo createRequestMapping(Method method, Object bean) {
        return new RequestMappingInfo(
                null,
                getPatterns(method),
                getRequestMethods(method),
                new ParamsRequestCondition(),
                new HeadersRequestCondition(),
                new ConsumesRequestCondition(),
                new ProducesRequestCondition(),
                null);
    }

    private PatternsRequestCondition getPatterns(Method method) {
        String classPart = method.getDeclaringClass().getSimpleName().replace("Service", "");
        String methodPart = method.getName();
        if (methodPart.startsWith("get") && methodPart.length() > "get".length()) {
            methodPart = methodPart.replace("get", "");
        } else {
            if (methodPart.length() == 1) {
                methodPart = methodPart.toUpperCase();
            } else {
                methodPart = methodPart.substring(0, 1).toUpperCase() + methodPart.substring(1);
            }
        }
        String mapping = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, classPart) + "/" + CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, methodPart);
        return new PatternsRequestCondition(mapping);
    }

    private RequestMethodsRequestCondition getRequestMethods(Method method) {
        return new RequestMethodsRequestCondition(
                RequestMethod.POST
        );
    }

}

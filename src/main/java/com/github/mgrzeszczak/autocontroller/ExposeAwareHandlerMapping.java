package com.github.mgrzeszczak.autocontroller;

import org.springframework.util.ReflectionUtils;
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
        return new PatternsRequestCondition(MappingResolver.resolvePath(method.getDeclaringClass().getSimpleName(), method.getName()));
    }

    private RequestMethodsRequestCondition getRequestMethods(Method method) {
        return new RequestMethodsRequestCondition(MappingResolver.resolveHttpMethod(method));
    }

}

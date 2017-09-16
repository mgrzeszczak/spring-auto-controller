package com.github.mgrzeszczak.autocontroller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import strman.Strman;

import java.lang.reflect.Method;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class MappingResolver {

    static String resolvePath(String className, String methodName) {
        className = Strman.removeRight(className, "Service", true);
        methodName = Strman.capitalize(methodName.length() > "get".length() ? Strman.removeLeft(methodName, "get", true) : methodName);

        return Stream.of(className, methodName)
                .map(Strman::toKebabCase)
                .collect(Collectors.joining("/"));
    }

    static RequestMethod resolveHttpMethod(Method method) {
        Expose annotation = method.getAnnotation(Expose.class);
        return Stream.of(method.getParameters())
                .flatMap(p -> Stream.of(p.getAnnotations()))
                .anyMatch(a -> a.annotationType().equals(RequestBody.class)) ? RequestMethod.POST : RequestMethod.GET;
    }

}

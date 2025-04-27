
/*
 * All rights Reserved, Designed By zhongj
 * @Description:
 * @author: zhongj
 * @date: 2024-07-03 16:42:03
 * @Copyright: 2024 www.featherfly.cn Inc. All rights reserved.
 */
package cn.featherfly.validation.executable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.executable.ExecutableValidator;

import cn.featherfly.validation.internal.InternalUtils;
import cn.featherfly.validation.metadata.ConstraintViolation;
import cn.featherfly.validation.metadata.JavaxConstraintViolation;

/**
 * JavaxExecutableValidator.
 *
 * @author zhongj
 */
public class JavaxExecutableValidator implements cn.featherfly.validation.executable.ExecutableValidator {

    private final ExecutableValidator executableValidator;

    private final Function<String, ? extends RuntimeException> exceptionConstractor;

    /**
     * Instantiates a new javax executable validator.
     *
     * @param executableValidator the executable validator
     * @param exceptionConstractor the exception constractor
     */
    public JavaxExecutableValidator(ExecutableValidator executableValidator,
        Function<String, ? extends RuntimeException> exceptionConstractor) {
        super();
        this.executableValidator = executableValidator;
        this.exceptionConstractor = exceptionConstractor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Set<ConstraintViolation<T>> validateParameters(T object, Method method, Object[] parameterValues,
        Class<?>... groups) {
        return executableValidator.validateParameters(object, method, parameterValues, groups).stream()
            .map(cv -> new JavaxConstraintViolation<>(cv)).collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Set<ConstraintViolation<T>> validateReturnValue(T object, Method method, Object returnValue,
        Class<?>... groups) {
        return executableValidator.validateReturnValue(object, method, returnValue, groups).stream()
            .map(cv -> new JavaxConstraintViolation<>(cv)).collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Set<ConstraintViolation<T>> validateConstructorParameters(Constructor<? extends T> constructor,
        Object[] parameterValues, Class<?>... groups) {
        Set<javax.validation.ConstraintViolation<T>> set = executableValidator
            .validateConstructorParameters(constructor, parameterValues, groups);
        return set.stream().map(cv -> new JavaxConstraintViolation<>(cv)).collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Set<ConstraintViolation<T>> validateConstructorReturnValue(Constructor<? extends T> constructor,
        T createdObject, Class<?>... groups) {
        Set<javax.validation.ConstraintViolation<T>> set = executableValidator
            .validateConstructorReturnValue(constructor, createdObject, groups);
        return set.stream().map(cv -> new JavaxConstraintViolation<>(cv)).collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> void validateParametersThrow(T object, Method method, Object[] parameterValues, Class<?>... groups) {
        Set<javax.validation.ConstraintViolation<T>> cvs = executableValidator.validateParameters(object, method,
            parameterValues, groups);
        if (cvs.isEmpty()) {
            return;
        }

        List<String> names = new ArrayList<>();
        StringBuilder errorMessage = new StringBuilder();
        String className = object instanceof Class ? ((Class<?>) object).getName() : object.getClass().getName();
        errorMessage.append(className).append(".").append(InternalUtils.getMethodDescp(method));

        for (javax.validation.ConstraintViolation<T> cv : cvs) {
            String name = cv.getPropertyPath().toString().substring(method.getName().length() + 1);
            names.add(" " + name + cv.getMessage() + ",");
        }
        names.sort(String::compareTo);
        for (String name : names) {
            errorMessage.append(name);
        }
        errorMessage.deleteCharAt(errorMessage.length() - 1);
        throw exceptionConstractor.apply(errorMessage.toString());
    }
}

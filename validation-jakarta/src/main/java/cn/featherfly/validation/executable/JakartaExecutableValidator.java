
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
import java.util.Set;
import java.util.stream.Collectors;

import cn.featherfly.validation.metadata.ConstraintViolation;
import cn.featherfly.validation.metadata.JakartaConstraintViolation;
import jakarta.validation.executable.ExecutableValidator;

/**
 * JavaxExecutableValidator.
 *
 * @author zhongj
 */
public class JakartaExecutableValidator implements cn.featherfly.validation.executable.ExecutableValidator {

    private final ExecutableValidator executableValidator;

    /**
     * Instantiates a new javax executable validator.
     *
     * @param executableValidator the executable validator
     */
    public JakartaExecutableValidator(ExecutableValidator executableValidator) {
        super();
        this.executableValidator = executableValidator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Set<ConstraintViolation<T>> validateParameters(T object, Method method, Object[] parameterValues,
        Class<?>... groups) {
        return executableValidator.validateParameters(object, method, parameterValues, groups).stream()
            .map(cv -> new JakartaConstraintViolation<>(cv)).collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Set<ConstraintViolation<T>> validateReturnValue(T object, Method method, Object returnValue,
        Class<?>... groups) {
        return executableValidator.validateReturnValue(object, method, returnValue, groups).stream()
            .map(cv -> new JakartaConstraintViolation<>(cv)).collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Set<ConstraintViolation<T>> validateConstructorParameters(Constructor<? extends T> constructor,
        Object[] parameterValues, Class<?>... groups) {
        Set<jakarta.validation.ConstraintViolation<T>> set = executableValidator
            .validateConstructorParameters(constructor, parameterValues, groups);
        return set.stream().map(cv -> new JakartaConstraintViolation<>(cv)).collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Set<ConstraintViolation<T>> validateConstructorReturnValue(Constructor<? extends T> constructor,
        T createdObject, Class<?>... groups) {
        Set<jakarta.validation.ConstraintViolation<T>> set = executableValidator
            .validateConstructorReturnValue(constructor, createdObject, groups);
        return set.stream().map(cv -> new JakartaConstraintViolation<>(cv)).collect(Collectors.toSet());
    }

}

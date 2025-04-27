
/*
 * All rights Reserved, Designed By zhongj
 * @Description:
 * @author: zhongj
 * @date: 2024-07-03 16:33:03
 * @Copyright: 2024 www.featherfly.cn Inc. All rights reserved.
 */
package cn.featherfly.validation;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import cn.featherfly.validation.executable.ExecutableValidator;
import cn.featherfly.validation.executable.JakartaExecutableValidator;
import cn.featherfly.validation.metadata.ConstraintViolation;
import cn.featherfly.validation.metadata.JakartaConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.metadata.BeanDescriptor;

/**
 * JakartaValidator.
 *
 * @author zhongj
 */
public class JakartaValidator implements cn.featherfly.validation.Validator {

    private final Validator validator;

    private final Function<String, ? extends RuntimeException> exceptionConstractor;

    /**
     * Instantiates a new javax validator.
     *
     * @param validator the validator
     * @param exceptionConstracto the exception constracto
     */
    public JakartaValidator(Validator validator, Function<String, ? extends RuntimeException> exceptionConstracto) {
        super();
        this.validator = validator;
        exceptionConstractor = exceptionConstracto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups) {
        return validator.validate(object, groups).stream().map(cv -> new JakartaConstraintViolation<>(cv))
            .collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Set<ConstraintViolation<T>> validateProperty(T object, String propertyName, Class<?>... groups) {
        return validator.validateProperty(object, propertyName, groups).stream()
            .map(cv -> new JakartaConstraintViolation<>(cv)).collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Set<ConstraintViolation<T>> validateValue(Class<T> beanType, String propertyName, Object value,
        Class<?>... groups) {
        return validator.validateValue(beanType, propertyName, value, groups).stream()
            .map(cv -> new JakartaConstraintViolation<>(cv)).collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T unwrap(Class<T> type) {
        return validator.unwrap(type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExecutableValidator forExecutables() {
        return new JakartaExecutableValidator(validator.forExecutables(), exceptionConstractor);
    }

    /**
     * Gets the constraints for class.
     *
     * @param clazz the clazz
     * @return the constraints for class
     */
    public BeanDescriptor getConstraintsForClass(Class<?> clazz) {
        return validator.getConstraintsForClass(clazz);
    }
}

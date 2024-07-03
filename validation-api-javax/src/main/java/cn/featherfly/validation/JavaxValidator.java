
/*
 * All rights Reserved, Designed By zhongj
 * @Description:
 * @author: zhongj
 * @date: 2024-07-03 16:33:03
 * @Copyright: 2024 www.featherfly.cn Inc. All rights reserved.
 */
package cn.featherfly.validation;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Validator;
import javax.validation.metadata.BeanDescriptor;

import cn.featherfly.validation.executable.ExecutableValidator;
import cn.featherfly.validation.executable.JavaxExecutableValidator;
import cn.featherfly.validation.metadata.ConstraintViolation;
import cn.featherfly.validation.metadata.JavaxConstraintViolation;

/**
 * ValidatorProxy.
 *
 * @author zhongj
 */
public class JavaxValidator implements cn.featherfly.validation.Validator {

    private Validator validator;

    /**
     * Instantiates a new javax validator.
     *
     * @param validator the validator
     */
    public JavaxValidator(Validator validator) {
        super();
        this.validator = validator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups) {
        return validator.validate(object, groups).stream().map(cv -> new JavaxConstraintViolation<>(cv))
            .collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Set<ConstraintViolation<T>> validateProperty(T object, String propertyName, Class<?>... groups) {
        return validator.validateProperty(object, propertyName, groups).stream()
            .map(cv -> new JavaxConstraintViolation<>(cv)).collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Set<ConstraintViolation<T>> validateValue(Class<T> beanType, String propertyName, Object value,
        Class<?>... groups) {
        return validator.validateValue(beanType, propertyName, value, groups).stream()
            .map(cv -> new JavaxConstraintViolation<>(cv)).collect(Collectors.toSet());
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
        return new JavaxExecutableValidator(validator.forExecutables());
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


/*
 * All rights Reserved, Designed By zhongj
 * @Description:
 * @author: zhongj
 * @date: 2024-07-03 16:13:03
 * @Copyright: 2024 www.featherfly.cn Inc. All rights reserved.
 */
package cn.featherfly.validation;

import java.lang.annotation.Annotation;
import java.util.Set;

import cn.featherfly.validation.executable.ExecutableValidator;
import cn.featherfly.validation.metadata.ConstraintViolation;

/**
 * Validator.
 *
 * @author zhongj
 * @since 0.1.0
 */
public interface Validator {

    /**
     * Validates all constraints on {@code object}.
     *
     * @param <T> the type of the object to validate
     * @param object object to validate
     * @param groups the group or list of groups targeted for validation
     * @return constraint violations or an empty set if none
     * @throws IllegalArgumentException if object is {@code null}
     *         or if {@code null} is passed to the varargs groups
     */
    <T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups);

    /**
     * Validates all constraints placed on the property of {@code object}
     * named {@code propertyName}.
     *
     * @param <T> the type of the object to validate
     * @param object object to validate
     * @param propertyName property to validate (i.e. field and getter constraints)
     * @param groups the group or list of groups targeted for validation
     * @return constraint violations or an empty set if none
     * @throws IllegalArgumentException if {@code object} is {@code null},
     *         if {@code propertyName} is {@code null}, empty or not a valid object property
     *         or if {@code null} is passed to the varargs groups
     */
    <T> Set<ConstraintViolation<T>> validateProperty(T object, String propertyName, Class<?>... groups);

    /**
     * Validates all constraints placed on the property named {@code propertyName}
     * of the class {@code beanType} would the property value be {@code value}.
     * <p>
     * {@link ConstraintViolation} objects return {@code null} for
     * {@link ConstraintViolation#getRootBean()} and
     * {@link ConstraintViolation#getLeafBean()}.
     *
     * @param <T> the type of the object to validate
     * @param beanType the bean type
     * @param propertyName property to validate
     * @param value property value to validate
     * @param groups the group or list of groups targeted for validation .
     * @return constraint violations or an empty set if none
     * @throws IllegalArgumentException if {@code beanType} is {@code null},
     *         if {@code propertyName} is {@code null}, empty or not a valid object property
     *         or if {@code null} is passed to the varargs groups
     */
    <T> Set<ConstraintViolation<T>> validateValue(Class<T> beanType, String propertyName, Object value,
        Class<?>... groups);

    /**
     * Unwrap.
     *
     * @param <T> the generic type
     * @param type the type
     * @return the t
     */
    <T> T unwrap(Class<T> type);

    /**
     * For executables.
     *
     * @return the executable validator
     */
    ExecutableValidator forExecutables();

    /**
     * Checks if is constraint.
     *
     * @param <A> the generic type
     * @param constraintType the constraint type
     * @return true, if is constraint
     */
    <A extends Annotation> boolean isConstraint(Class<A> constraintType);
}


/*
 * All rights Reserved, Designed By zhongj
 * @Description:
 * @author: zhongj
 * @date: 2024-07-03 16:36:03
 * @Copyright: 2024 www.featherfly.cn Inc. All rights reserved.
 */
package cn.featherfly.validation.metadata;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.metadata.ConstraintDescriptor;

/**
 * JakartaConstraintViolation.
 *
 * @author zhongj
 * @param <T> the generic type
 */
public class JakartaConstraintViolation<T> implements cn.featherfly.validation.metadata.ConstraintViolation<T> {

    private final ConstraintViolation<T> constraintViolation;

    /**
     * Instantiates a new javax constraint violation.
     *
     * @param constraintViolation the constraint violation
     */
    public JakartaConstraintViolation(ConstraintViolation<T> constraintViolation) {
        super();
        this.constraintViolation = constraintViolation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMessage() {
        return constraintViolation.getMessage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMessageTemplate() {
        return constraintViolation.getMessageTemplate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getRootBean() {
        return constraintViolation.getRootBean();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<T> getRootBeanClass() {
        return constraintViolation.getRootBeanClass();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getLeafBean() {
        return constraintViolation.getLeafBean();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object[] getExecutableParameters() {
        return constraintViolation.getExecutableParameters();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getExecutableReturnValue() {
        return constraintViolation.getExecutableReturnValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getInvalidValue() {
        return constraintViolation.getInvalidValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <U> U unwrap(Class<U> type) {
        return constraintViolation.unwrap(type);
    }

    /**
     * Gets the property path.
     *
     * @return the property path
     */
    public Path getPropertyPath() {
        return constraintViolation.getPropertyPath();
    }

    /**
     * Gets the constraint descriptor.
     *
     * @return the constraint descriptor
     */
    public ConstraintDescriptor<?> getConstraintDescriptor() {
        return constraintViolation.getConstraintDescriptor();
    }

}

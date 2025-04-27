
/*
 * All rights Reserved, Designed By zhongj
 * @Description:
 * @author: zhongj
 * @date: 2025-04-25 03:39:25
 * @Copyright: 2025 www.featherfly.cn Inc. All rights reserved.
 */
package cn.featherfly.validation;

import java.lang.reflect.Method;
import java.util.Locale;

import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.HibernateValidator;
import org.testng.annotations.Test;

import cn.featherfly.validation.executable.ExecutableValidator;
import cn.featherfly.validation.executable.JavaxExecutableValidator;

/**
 * TestValidator.
 *
 * @author zhongj
 */
public class TestValidatorMethod implements ITest {

    private ExecutableValidator executableValidator;

    public TestValidatorMethod() {
        //        Locale.setDefault(Locale.US);
        ValidatorFactory factory = Validation.byProvider(HibernateValidator.class).configure()
            .defaultLocale(Locale.SIMPLIFIED_CHINESE) //
            .buildValidatorFactory();
        executableValidator = new JavaxExecutableValidator(factory.getValidator().forExecutables(),
            ValidationException::new);
    }

    private Method getMethod(String name, Class<?>... parameterTypes) {
        try {
            return this.getClass().getMethod(name, parameterTypes);
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void hello(String msg) {
        executableValidator.validateParametersThrow(this, getMethod("hello", String.class), new Object[] { msg });
    }

    @Override
    public void hello2(String msg, Teacher teacher) {
        executableValidator.validateParametersThrow(this, getMethod("hello2", String.class, Teacher.class),
            new Object[] { msg, teacher });
    }

    @Test(expectedExceptions = ValidationException.class,
        expectedExceptionsMessageRegExp = "cn\\.featherfly\\.validation\\.TestValidatorMethod\\.hello\\(String msg\\) msg不能为null")
    public void testHelloNull() {
        hello(null);
    }

    @Test(expectedExceptions = ValidationException.class,
        expectedExceptionsMessageRegExp = "cn\\.featherfly\\.validation\\.TestValidatorMethod\\.hello2\\(String msg, Teacher teacher\\) msg不能为null, teacher不能为null")
    public void testHello2Null() {
        hello2(null, null);
    }

    @Test(expectedExceptions = ValidationException.class,
        expectedExceptionsMessageRegExp = "cn\\.featherfly\\.validation\\.TestValidatorMethod\\.hello2\\(String msg, Teacher teacher\\) msg不能为null, teacher.id不能为null, teacher.name不能为null")
    public void testHello2Null2() {
        hello2(null, new Teacher());
    }
}

interface ITest {
    void hello(@NotNull String msg);

    void hello2(@NotNull String msg, @Valid @NotNull Teacher teacher);
}

class Teacher {
    @NotNull
    private Integer id;

    @NotNull
    private String name;

    /**
     * get id value
     *
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * set id value
     *
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * get name value
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * set name value
     *
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }
}
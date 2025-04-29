
/*
 * All rights Reserved, Designed By zhongj
 * @Description:
 * @author: zhongj
 * @date: 2025-04-25 03:39:25
 * @Copyright: 2025 www.featherfly.cn Inc. All rights reserved.
 */
package cn.featherfly.validation;

import java.lang.reflect.Constructor;
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

    /**
     * Instantiates a new test validator method.
     */
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

    @Test(expectedExceptions = ValidationException.class,
        expectedExceptionsMessageRegExp = "cn\\.featherfly\\.validation\\.TestValidatorMethod\\.getName\\(\\) <return value>不能为null")
    public void testGetNull() {
        getName();
    }

    @Test(expectedExceptions = ValidationException.class,
        expectedExceptionsMessageRegExp = "cn\\.featherfly\\.validation\\.TestValidatorMethod\\.getTeacher\\(Teacher teacher\\) <return value>不能为null")
    public void testGetTeacher() {
        getTeacher(null);
    }

    @Test(expectedExceptions = ValidationException.class,
        expectedExceptionsMessageRegExp = "cn\\.featherfly\\.validation\\.TestValidatorMethod\\.getTeacher\\(Teacher teacher\\) <return value>.id不能为null, <return value>.name不能为null")
    public void testGetTeacher2() {
        getTeacher(new Teacher());
    }

    @Test(expectedExceptions = ValidationException.class,
        expectedExceptionsMessageRegExp = "cn\\.featherfly\\.validation\\.Teacher\\(ExecutableValidator validator, Integer id\\) id不能为null")
    public void testConstract() {
        new Teacher(executableValidator, null);
    }

    @Test(expectedExceptions = ValidationException.class,
        expectedExceptionsMessageRegExp = "cn\\.featherfly\\.validation\\.Teacher\\(ExecutableValidator validator, Integer id, String name\\) <return value>.name不能为null")
    public void testConstract2() {
        new Teacher(executableValidator, 1, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hello(String msg) {
        executableValidator.validateParametersThrow(this, getMethod("hello", String.class), new Object[] { msg });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hello2(String msg, Teacher teacher) {
        executableValidator.validateParametersThrow(this, getMethod("hello2", String.class, Teacher.class),
            new Object[] { msg, teacher });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        executableValidator.validateReturnValueThrow(this, getMethod("getName"), null);
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Teacher getTeacher(Teacher teacher) {
        executableValidator.validateReturnValueThrow(this, getMethod("getTeacher", Teacher.class), teacher);
        return teacher;
    }
}

interface ITest {
    void hello(@NotNull String msg);

    void hello2(@NotNull String msg, @Valid @NotNull Teacher teacher);

    @NotNull
    String getName();

    @NotNull
    @Valid
    Teacher getTeacher(Teacher teacher);
}

class Teacher {
    @NotNull
    private Integer id;

    @NotNull
    private String name;

    /**
     * Instantiates a new teacher.
     */
    public Teacher() {

    }

    /**
     * Instantiates a new teacher.
     *
     * @param id the id
     */
    public Teacher(ExecutableValidator validator, @NotNull Integer id) {
        super();

        try {
            validator.validateConstructorParametersThrow(
                this.getClass().getConstructor(ExecutableValidator.class, Integer.class),
                new Object[] { validator, id });
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException(e);
        }

        this.id = id;
    }

    /**
     * Instantiates a new teacher.
     *
     * @param validator the validator
     * @param id the id
     * @param name the name
     */
    @Valid
    public Teacher(ExecutableValidator validator, Integer id, String name) {
        super();

        Constructor<?> c = null;
        try {
            c = this.getClass().getConstructor(ExecutableValidator.class, Integer.class, String.class);
            validator.validateConstructorParametersThrow(
                c, new Object[] { validator, id, name });
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException(e);
        }

        this.id = id;
        this.name = name;

        validator.validateConstructorReturnValueThrow(c, this);
    }

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
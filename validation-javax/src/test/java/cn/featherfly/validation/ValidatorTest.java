
/*
 * All rights Reserved, Designed By zhongj
 * @Description:
 * @author: zhongj
 * @date: 2025-04-25 03:39:25
 * @Copyright: 2025 www.featherfly.cn Inc. All rights reserved.
 */
package cn.featherfly.validation;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Negative;
import javax.validation.constraints.NegativeOrZero;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.constraints.CodePointLength;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.Currency;
import org.hibernate.validator.constraints.Length;
import org.testng.annotations.Test;

/**
 * TestValidator.
 *
 * @author zhongj
 */
public class ValidatorTest {

    private Validator validator;

    public ValidatorTest() {
        ValidatorFactory factory = Validation.byProvider(HibernateValidator.class).configure()
            .defaultLocale(Locale.SIMPLIFIED_CHINESE) //
            .buildValidatorFactory();
        validator = new JavaxValidator(factory.getValidator(), ValidationException::new);
    }

    @Test
    public void defaultConstraint() {
        assertTrue(validator.isConstraint(AssertFalse.class));
        assertTrue(validator.isConstraint(AssertTrue.class));
        assertTrue(validator.isConstraint(DecimalMax.class));
        assertTrue(validator.isConstraint(DecimalMin.class));
        assertTrue(validator.isConstraint(Digits.class));
        assertTrue(validator.isConstraint(Email.class));
        assertTrue(validator.isConstraint(Future.class));
        assertTrue(validator.isConstraint(FutureOrPresent.class));
        assertTrue(validator.isConstraint(Max.class));
        assertTrue(validator.isConstraint(Min.class));
        assertTrue(validator.isConstraint(Negative.class));
        assertTrue(validator.isConstraint(NegativeOrZero.class));
        assertTrue(validator.isConstraint(NotBlank.class));
        assertTrue(validator.isConstraint(NotEmpty.class));
        assertTrue(validator.isConstraint(NotNull.class));
        assertTrue(validator.isConstraint(Null.class));
        assertTrue(validator.isConstraint(Past.class));
        assertTrue(validator.isConstraint(PastOrPresent.class));
        assertTrue(validator.isConstraint(Pattern.class));
        assertTrue(validator.isConstraint(Positive.class));
        assertTrue(validator.isConstraint(PositiveOrZero.class));
        assertTrue(validator.isConstraint(Size.class));
    }

    @Test
    public void hibernateConstraint() {
        assertTrue(validator.isConstraint(CodePointLength.class));
        assertTrue(validator.isConstraint(CreditCardNumber.class));
        assertTrue(validator.isConstraint(Currency.class));
        assertTrue(validator.isConstraint(Length.class));
    }

    @Test
    public void notConstraint() {
        assertFalse(validator.isConstraint(Nonnull.class));
    }

}
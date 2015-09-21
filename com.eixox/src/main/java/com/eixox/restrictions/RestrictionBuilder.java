package com.eixox.restrictions;

import java.lang.annotation.Annotation;

public class RestrictionBuilder {

	public Restriction build(Annotation annotation) {

		if (annotation instanceof Age)
			return new AgeRestriction((Age) annotation);

		if (annotation instanceof Cpf)
			return new CpfRestriction();

		if (annotation instanceof Email)
			return new EmailRestriction();

		if (annotation instanceof Length)
			return new LengthRestriction((Length) annotation);

		if (annotation instanceof MaxLength)
			return new MaxLengthRestriction((MaxLength) annotation);

		if (annotation instanceof MaxNumberExclusive)
			return new MaxNumberExclusiveRestriction((MaxNumberExclusive) annotation);

		if (annotation instanceof MaxNumberInclusive)
			return new MaxNumberInclusiveRestriction((MaxNumberInclusive) annotation);

		if (annotation instanceof MinLength)
			return new MinLengthRestriction((MinLength) annotation);

		if (annotation instanceof MinNumberExclusive)
			return new MinNumberExclusiveRestriction((MinNumberExclusive) annotation);

		if (annotation instanceof MinNumberInclusive)
			return new MinNumberInclusiveRestriction((MinNumberInclusive) annotation);

		if (annotation instanceof NumberRange)
			return new NumberRangeRestriction((NumberRange) annotation);

		if (annotation instanceof Required)
			return new RequiredRestriction();

		if (annotation instanceof Name)
			return new NameRestriction();

		try {
			return (Restriction) Class.forName(annotation.annotationType().getCanonicalName() + "Restriction").getConstructor(annotation.annotationType()).newInstance(annotation);
		} catch (Exception e) {
			return null;
		}

	}

	public static final RestrictionBuilder defaultInstance = new RestrictionBuilder();
}

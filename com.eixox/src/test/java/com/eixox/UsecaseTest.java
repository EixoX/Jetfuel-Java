package com.eixox;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class UsecaseTest {

	@Test
	public void testAPresentationExecution() {

		UsecaseExample example = new UsecaseExample();
		UsecaseResult result = null;

		result = example.execute();
		Assert.assertTrue("Direct execution should fail validation", result.resultType == UsecaseResultType.VALIDATION_FAILED);

		example.presentation.get("aLongProperty").value = "1";
		example.presentation.get("aStringProperty").value = "Teste";
		example.parsePresentation();

		result = example.execute();
		Assert.assertTrue("Main flow should return successful", result.resultType == UsecaseResultType.SUCCESS);

	}

	@Test
	public void testSimpleExecution() {

		UsecaseExample example = new UsecaseExample();
		UsecaseResult result = null;

		result = example.execute();
		Assert.assertTrue("Direct execution should fail validation", result.resultType == UsecaseResultType.VALIDATION_FAILED);

		example.aLongProperty = 1;
		example.aStringProperty = "Teste";

		result = example.execute();
		Assert.assertTrue("Main flow should return successful", result.resultType == UsecaseResultType.SUCCESS);
	}

	@Test
	public void testBPresentationExecution() {

		UsecaseExample example = new UsecaseExample();
		UsecaseResult result = null;

		result = example.execute();
		Assert.assertTrue("Direct execution should fail validation", result.resultType == UsecaseResultType.VALIDATION_FAILED);

		example.presentation.get("aLongProperty").value = "1";
		example.presentation.get("aStringProperty").value = "Teste";
		example.parsePresentation();

		result = example.execute();
		Assert.assertTrue("Main flow should return successful", result.resultType == UsecaseResultType.SUCCESS);

	}

	@Test
	public void testInheritedSimpleValidation() {

		UsecaseInherited inherited = new UsecaseInherited();

		inherited.aLongProperty = 1;
		inherited.aStringProperty = "Teste 1,2 3";
		inherited.anotherProperty = "Hey";

		Assert.assertFalse("The usecase should not validate", inherited.validate() != UsecaseResultType.SUCCESS);

		inherited.andAThird = new Date();
		Assert.assertTrue("The usecase should validate", inherited.validate() == UsecaseResultType.SUCCESS);
	}
}

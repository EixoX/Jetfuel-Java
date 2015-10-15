import org.junit.Assert;
import org.junit.Test;

import com.eixox.generators.CreditCardGenerators;

public class EloGenerator {

	@Test
	public void generateEloNumbers() {
		for (int i = 0; i < 1000; i++)
			System.out.println(CreditCardGenerators.ELO.generate());

		Assert.assertTrue(true);
	}
}

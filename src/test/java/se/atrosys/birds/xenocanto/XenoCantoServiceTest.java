package se.atrosys.birds.xenocanto;

import org.junit.Assert;
import org.junit.Test;
import se.atrosys.birds.model.Bird;

import static org.junit.Assert.*;

/**
 * TODO write documentation
 */
public class XenoCantoServiceTest {
	@Test
	public void shouldWork() {
		XenoCantoResult result = new XenoCantoService().search(Bird.builder()
			.scientificName("Pica pica")
			.build());

		Assert.assertNotNull(result);
	}

}
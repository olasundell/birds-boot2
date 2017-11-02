package se.atrosys.birds.media.xenocanto;

import org.junit.Assert;
import org.junit.Test;
import se.atrosys.birds.model.Bird;

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
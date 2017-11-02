package se.atrosys.birds.taxonomy.avibase;

import org.junit.Assert;
import org.junit.Test;
import se.atrosys.birds.model.Region;

import java.util.List;

/**
 * TODO write documentation
 */
public class AviBaseRegionServiceTest {
	@Test
	public void shouldReadRegionList() {
		List<Region> result = new AviBaseRegionService().readRegions();
		Assert.assertNotNull("Should have results", result);
		Assert.assertFalse("Should have non-empty results", result.isEmpty());
	}

}
package se.atrosys.birds.taxonomy.ebird;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * TODO write documentation
 */
public class EbirdServiceTest {

	private EbirdService ebirdService;

	@Before
	public void setUp() throws Exception {
		ebirdService = new EbirdService();
	}

	@Test
	public void shouldRead() {
		List<EbirdSpecies> result = ebirdService.readSpecies();

		Assert.assertNotNull(result);
		Assert.assertFalse(result.isEmpty());
	}
}
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

	@Test
	public void shouldHaveCombDuck() {
		Map<String, EbirdSpecies> engNameMap = ebirdService.engNameMap();
		Map<String, EbirdSpecies> sciNameMap = ebirdService.sciNameMap();

		Assert.assertTrue(engNameMap.containsKey("comb duck"));
		Assert.assertTrue(sciNameMap.containsKey("sarkidiornis sylvicola"));
	}
}
package se.atrosys.birds.taxonomy.avibase;

import org.jsoup.Jsoup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import se.atrosys.birds.model.Region;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.mockito.Mockito.mock;

/**
 * TODO write documentation
 */
public class AviBaseServiceTest {
	AviBaseService service;
	@Before
	public void setUp() throws Exception {
		service = new AviBaseService();
	}

	@Test
	@Ignore("This is used to create all avibase files, not needed unless regeneration of all files wanted")
	public void completeRun() throws IOException {
		List<Region> regions = new AviBaseRegionService().readRegions();

		List<AviBaseResult> aviBaseResults;

		for (Region region: regions) {
			service.writeToFile("/tmp/" + region.getCode() + "-ioc.json", service.getAviBaseStuff(region));
		}
	}

	@Test
	public void shouldCreateList() throws IOException {
		String str = new String(Files.readAllBytes(Paths.get(new ClassPathResource("avibase-eur.html").getFile().getAbsolutePath())));

		AviBaseResult result = service.createResult(Region.builder()
				.code("EUR")
				.id(1)
				.name("Europe")
				.build(),
			Jsoup.parse(str));

		Assert.assertEquals(900, result.getRegionalScarcities().size());

		service.writeToFile("/tmp/result.json", result);
	}

}
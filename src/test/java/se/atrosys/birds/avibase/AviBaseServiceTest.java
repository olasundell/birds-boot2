package se.atrosys.birds.avibase;

import org.jsoup.Jsoup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import se.atrosys.birds.model.Bird;
import se.atrosys.birds.model.Region;
import se.atrosys.birds.repository.BirdRepository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
			service.writeToFile("/tmp/" + region.getCode() + ".json", service.getAviBaseStuff(region));
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
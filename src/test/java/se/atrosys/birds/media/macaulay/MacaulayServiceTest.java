package se.atrosys.birds.media.macaulay;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import se.atrosys.birds.model.Bird;
import se.atrosys.birds.model.BirdName;
import se.atrosys.birds.model.Language;
import se.atrosys.birds.model.MediaType;

import java.io.IOException;
import java.util.Collections;
import java.util.Locale;

/**
 * TODO write documentation
 */
public class MacaulayServiceTest {

	private MacaulayService macaulayService;

	@Before
	public void setUp() throws Exception {
		macaulayService = new MacaulayService();
	}

	@Test
	public void shouldMapOrdinaryResults() throws IOException {
		ClassPathResource cpr = new ClassPathResource("sound/macaulay/orioulus_traillii.json");

		MacaulayResponse response = new ObjectMapper().readValue(cpr.getFile(), MacaulayResponse.class);
		Assert.assertNotNull(response);
	}

	@Test
	public void shouldMapAudioSearch() throws IOException {
		ClassPathResource cpr = new ClassPathResource("sound/macaulay/pyrrhula_pyrrhula_audio.json");

		MacaulayResponse response = new ObjectMapper().readValue(cpr.getFile(), MacaulayResponse.class);
		Assert.assertNotNull(response);
	}

	@Test
	public void shouldSearch() {
		MacaulayResponse result = macaulayService.search(Bird.builder()
			.ebirdTaxonId("eurmag1")
			.birdNames(Collections.singletonList(BirdName.builder()
				.name("Eurasian Magpie")
				.language(Language.builder().locale(Locale.ENGLISH).build())
				.build()))
			.scientificName("pica pica")
			.build(), MediaType.PHOTO);

		Assert.assertNotNull(result);
	}

}
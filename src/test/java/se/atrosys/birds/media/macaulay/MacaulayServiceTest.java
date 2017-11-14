package se.atrosys.birds.media.macaulay;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.client.RestTemplate;
import se.atrosys.birds.model.Bird;
import se.atrosys.birds.model.BirdName;
import se.atrosys.birds.model.Language;
import se.atrosys.birds.model.MediaType;
import se.atrosys.birds.service.BirdService;

import java.io.IOException;
import java.util.Collections;
import java.util.Locale;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;

/**
 * TODO write documentation
 */
@RunWith(MockitoJUnitRunner.class)
public class MacaulayServiceTest {

	@Mock
	private RestTemplate restTemplate;
	@Mock
	private BirdService birdService;

	@InjectMocks
	@Spy
	private MacaulayService macaulayService;

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
		Mockito.when(restTemplate.getForObject(Mockito.anyString(), eq(MacaulayResponse.class)))
			.thenReturn(MacaulayResponse.builder().build());

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
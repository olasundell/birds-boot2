package se.atrosys.birds.media.xenocanto;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;
import se.atrosys.birds.model.Bird;

import static org.mockito.Matchers.eq;

/**
 * TODO write documentation
 */
@RunWith(MockitoJUnitRunner.class)
public class XenoCantoServiceTest {
	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	@Spy
	private XenoCantoService xenoCantoService;

	@Test
	public void shouldWork() {
		Mockito.when(restTemplate.getForObject(Mockito.anyString(), eq(XenoCantoResult.class)))
			.thenReturn(XenoCantoResult.builder().build());

		XenoCantoResult result = xenoCantoService.search(Bird.builder()
			.scientificName("Pica pica")
			.build());

		Assert.assertNotNull(result);
	}

}
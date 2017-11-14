package se.atrosys.birds.media.xenocanto;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import se.atrosys.birds.media.MediaService;
import se.atrosys.birds.model.Bird;
import se.atrosys.birds.model.Media;
import se.atrosys.birds.model.MediaType;

/**
 * TODO write documentation
 */
@Component
public class XenoCantoService implements MediaService {
	private final RestTemplate restTemplate;

	public XenoCantoService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public XenoCantoResult search(Bird bird) {
		return restTemplate.getForObject("http://www.xeno-canto.org/api/2/recordings?query=" + bird.getScientificName(), XenoCantoResult.class);
	}

	@Override
	public Media getSpecificMedia(Media.MediaHash hash) {
		return null;
	}

	@Override
	public Media getMedia(Bird bird, MediaType mediaType) {
		if (mediaType != MediaType.AUDIO) {
			throw new IllegalArgumentException("XenoCanto only has audio");
		}

		return Media.builder()
			.url(search(bird).getRecordings().get(0).getFile())
			.mediaType(MediaType.AUDIO)
			.build();
	}
}

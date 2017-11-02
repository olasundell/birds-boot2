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
	public XenoCantoResult search(Bird bird) {
		RestTemplate template = new RestTemplate();
		final XenoCantoResult xenoCantoResult = template.getForObject("http://www.xeno-canto.org/api/2/recordings?query=" + bird.getScientificName(), XenoCantoResult.class);
		return xenoCantoResult;
	}

	@Override
	public Media getMedia(Bird bird, MediaType mediaType) {
		return Media.builder()
			.url(search(bird).getRecordings().get(0).getFile())
			.mediaType(MediaType.AUDIO)
			.build();
	}
}

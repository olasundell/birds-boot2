package se.atrosys.birds.media.xenocanto;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import se.atrosys.birds.model.Bird;

/**
 * TODO write documentation
 */
@Component
public class XenoCantoService {
	public XenoCantoResult search(Bird bird) {
		RestTemplate template = new RestTemplate();
		final XenoCantoResult xenoCantoResult = template.getForObject("http://www.xeno-canto.org/api/2/recordings?query=" + bird.getScientificName(), XenoCantoResult.class);
		return xenoCantoResult;
	}
}

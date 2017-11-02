package se.atrosys.birds.media.avocet;

import org.springframework.stereotype.Component;
import se.atrosys.birds.media.MediaService;
import se.atrosys.birds.model.Bird;
import se.atrosys.birds.model.Media;
import se.atrosys.birds.model.MediaType;

/**
 * TODO write documentation
 */
@Component
public class AvocetService implements MediaService {
	@Override
	public Media getMedia(Bird bird, MediaType mediaType) {
		return null;
	}
}

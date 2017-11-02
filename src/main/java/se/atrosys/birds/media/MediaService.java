package se.atrosys.birds.media;

import se.atrosys.birds.exception.CountNotFindMediaException;
import se.atrosys.birds.model.Bird;
import se.atrosys.birds.model.Media;
import se.atrosys.birds.model.MediaType;

/**
 * TODO write documentation
 */
public interface MediaService {
	Media getMedia(Bird bird, MediaType mediaType) throws CountNotFindMediaException;
}

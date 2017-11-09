package se.atrosys.birds.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import se.atrosys.birds.exception.BirdFlickrException;
import se.atrosys.birds.exception.CountNotFindMediaException;
import se.atrosys.birds.media.MediaService;
import se.atrosys.birds.media.flickr.FlickrService;
import se.atrosys.birds.model.Bird;
import se.atrosys.birds.model.BirdPhoto;
import se.atrosys.birds.model.Media;
import se.atrosys.birds.model.MediaType;
import se.atrosys.birds.model.Response;
import se.atrosys.birds.media.xenocanto.XenoCantoService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * TODO write documentation
 */
@Component
public class ResponseService {
	private final BirdService birdService;
	private final MediaService mediaService;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public ResponseService(BirdService birdService,
	                       @Qualifier("macaulayService") MediaService mediaService) {
		this.birdService = birdService;
		this.mediaService = mediaService;
	}

	public Response createRandomResponse(String language, MediaType mediaType, String regionCode) {
		Bird bird = null;
		Optional<Media> media = Optional.empty();

		// TODO this is a fugly solution, improve
		for (int i = 0 ; i < 10 ; i++) {
			bird = birdService.findRandom(regionCode);
			media = findMedia(bird, mediaType);
			if (media.isPresent()) {
				break;
			}
		}

		return buildResponse(language, bird, media);
	}

	private Optional<Media> findMedia(Bird bird, MediaType mediaType) {
		try {
			return Optional.of(mediaService.getMedia(bird, mediaType));
		} catch (CountNotFindMediaException e) {
			logger.warn("Could not find media for " + bird.getScientificName());
		}

		return Optional.empty();
	}

	public Response getGivenBirdResponse(String language, MediaType mediaType, String regionCode, String sciName) {
		Bird bird = birdService.findByScientificName(sciName);
		Optional<Media> media = findMedia(bird, mediaType);

		return buildResponse(language, bird, media);
	}

	private Response buildResponse(String language, Bird bird, Optional<Media> media) {
		final List<Response.ResponseBird> collect = bird.getGenus()
			.getBirds()
			.stream()
			.map(b -> mapToRB(b, language))
			.collect(Collectors.toList());

		return Response.builder()
			.genusBirds(collect)
			.media(media.orElseThrow(() -> new IllegalStateException("Could not find proper media for a number of random birds, giving up")))
			.actualBird(mapToRB(bird, language))
			.build();
	}

	private Response.ResponseBird mapToRB(Bird bird, String language) {
		return Response.ResponseBird.builder()
			.genusName(bird.getGenusName())
			.name(bird.namesByLangCode().get(language))
			.scientificName(bird.getScientificName())
			.build();
	}
}

package se.atrosys.birds.service;

import org.springframework.stereotype.Component;
import se.atrosys.birds.flickr.FlickrService;
import se.atrosys.birds.model.Bird;
import se.atrosys.birds.model.BirdPhoto;
import se.atrosys.birds.model.Response;
import se.atrosys.birds.xenocanto.XenoCantoService;

import javax.xml.bind.JAXBException;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * TODO write documentation
 */
@Component
public class ResponseService {
	private final BirdService birdService;
	private final FlickrService flickrService;
	private final XenoCantoService xenoCantoService;

	public ResponseService(BirdService birdService,
	                       FlickrService flickrService, XenoCantoService xenoCantoService) {
		this.birdService = birdService;
		this.flickrService = flickrService;
		this.xenoCantoService = xenoCantoService;
	}

	public Response createResponse(String language) throws JAXBException {
		Bird bird = null;
		List<BirdPhoto> pictures = Collections.emptyList();

		while (pictures.isEmpty()) {
			bird = birdService.findRandom();
			pictures = flickrService.getPictures(bird);
		}

		final List<Response.ResponseBird> collect = bird.getGenus()
			.getBirds()
			.stream()
			.map(b -> mapToRB(b, language))
			.collect(Collectors.toList());

		return Response.builder()
			.genusBirds(collect)
			.pictureUrl(pictures.get(new Random(0).nextInt(pictures.size())).getNiftyUrl())
			.actualBird(mapToRB(bird, language))
			.build();
	}

	private Response.ResponseBird mapToRB(Bird bird, String language) {
		return Response.ResponseBird.builder()
			.genusName(bird.getGenusName())
			.name(bird.namesByLanguage().get(language.toLowerCase()))
			.scientificName(bird.getScientificName())
			.build();
	}
}

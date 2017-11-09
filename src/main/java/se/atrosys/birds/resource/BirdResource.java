package se.atrosys.birds.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.atrosys.birds.exception.BirdFlickrException;
import se.atrosys.birds.model.Bird;
import se.atrosys.birds.model.MediaType;
import se.atrosys.birds.model.Response;
import se.atrosys.birds.service.BirdService;
import se.atrosys.birds.service.ResponseService;

/**
 * TODO write documentation
 */
@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"} )
public class BirdResource {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final BirdService birdService;
	private final ResponseService responseService;

	public BirdResource(BirdService birdService, ResponseService responseService) {
		this.birdService = birdService;
		this.responseService = responseService;
	}

	@GetMapping("/random")
	public Response random(@RequestParam(required = false, name = "lang", defaultValue = "en_US") String language,
	                       @RequestParam(required = false, name = "mediaType", defaultValue = "photo") String mediaType,
	                       @RequestParam(required = false, name = "region", defaultValue = "WORLD") String regionCode,
	                       @RequestParam(required = false, name = "name", defaultValue = "") String name) throws BirdFlickrException {

		MediaType mt = MediaType.PHOTO;
		if (mediaType.equals("audio")) {
			mt = MediaType.AUDIO;
		}

		Response response;

		if (name.isEmpty()) {
			response = responseService.createRandomResponse(language, mt, regionCode);

			logger.info("Getting a random bird, {} {} lang {}",
				response.getActualBird().getScientificName(),
				response.getActualBird().getName(),
				language);
		} else {
			response = responseService.getGivenBirdResponse(language, mt, regionCode, name);

			logger.info("Getting a specific bird, {} {} lang {}",
				response.getActualBird().getScientificName(),
				response.getActualBird().getName(),
				language);
		}

		return response;
	}

	@GetMapping("/birds")
	public Page<Bird> all(Pageable pageable) {
		return birdService.findAll(pageable);
	}

	@GetMapping("/byregion/{region}")
	public Page<Bird> byRegion(@PathVariable String region) {
		return birdService.byRegion(region);
	}
}

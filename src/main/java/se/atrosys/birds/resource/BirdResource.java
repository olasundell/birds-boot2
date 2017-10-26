package se.atrosys.birds.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.atrosys.birds.model.Bird;
import se.atrosys.birds.model.Response;
import se.atrosys.birds.repository.BirdRepository;
import se.atrosys.birds.service.ResponseService;

import javax.xml.bind.JAXBException;

/**
 * TODO write documentation
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class BirdResource {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final BirdRepository birdRepository;
	private final ResponseService responseService;

	public BirdResource(BirdRepository birdRepository, ResponseService responseService) {
		this.birdRepository = birdRepository;
		this.responseService = responseService;
	}

	@GetMapping("/random")
	public Response random(@RequestParam(required = false, name ="lang", defaultValue = "english") String language) throws JAXBException {
		Response response = responseService.createResponse(language);

		logger.info("Getting a random bird, {} {}",
			response.getActualBird().getGenusName(),
			response.getActualBird().getScientificName());

		return response;
	}

	@GetMapping("/birds")
	public Page<Bird> all(Pageable pageable) {
		return birdRepository.findAll(pageable);
	}
}

package se.atrosys.birds.resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.atrosys.birds.model.Bird;
import se.atrosys.birds.repository.BirdRepository;

import java.util.List;

/**
 * TODO write documentation
 */
@RestController
@RequestMapping("/birds")
public class BirdResource {
	private final BirdRepository birdRepository;

	public BirdResource(BirdRepository birdRepository) {
		this.birdRepository = birdRepository;
	}

	@GetMapping
	public Page<Bird> all(Pageable pageable) {
		return birdRepository.findAll(pageable);
	}
}

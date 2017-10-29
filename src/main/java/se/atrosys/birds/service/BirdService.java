package se.atrosys.birds.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import se.atrosys.birds.model.Bird;
import se.atrosys.birds.repository.BirdRepository;

/**
 * TODO write documentation
 */
@Component
public class BirdService {
	private final BirdRepository birdRepository;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public BirdService(BirdRepository birdRepository) {
		this.birdRepository = birdRepository;
	}

	@Cacheable(cacheNames= "birds", key = "#scientificName.toLowerCase()")
	public Bird findByScientificName(String scientificName) {
		logger.trace("Finding by scientific name {}", scientificName);
		return birdRepository.findByScientificName(scientificName);
	}

	@CachePut(cacheNames = "birds", key = "#bird.scientificName.toLowerCase()")
	public Bird save(Bird bird) {
		if (bird == null) {
			throw new IllegalArgumentException("Bird is null");
		}

		return birdRepository.save(bird);
	}

	public Bird findRandom() {
		return birdRepository.findOne(birdRepository.randomId());
	}

	public Page<Bird> findAll(Pageable pageable) {
		return birdRepository.findAll(pageable);
	}
}

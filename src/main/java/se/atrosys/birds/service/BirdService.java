package se.atrosys.birds.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import se.atrosys.birds.model.Bird;
import se.atrosys.birds.repository.BirdRepository;
import se.atrosys.birds.repository.RegionRepository;
import se.atrosys.birds.repository.RegionalScarcityRepository;

/**
 * TODO write documentation
 */
@Component
public class BirdService {
	private final BirdRepository birdRepository;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public BirdService(BirdRepository birdRepository,
	                   RegionRepository regionRepository,
	                   RegionalScarcityRepository regionalScarcityRepository) {
		this.birdRepository = birdRepository;
	}

	@Cacheable(cacheNames= "birds", key = "#scientificName.toLowerCase()")
	public Bird findByScientificName(String scientificName) {
		logger.trace("Finding by scientific name {}", scientificName);
		return birdRepository.findByScientificName(scientificName.toLowerCase());
	}

	public Bird save(Bird bird) {
		if (bird == null) {
			throw new IllegalArgumentException("Bird is null");
		}

		return cachePut(birdRepository.save(bird));
	}

	@CachePut(cacheNames = "birds", key = "#bird.scientificName.toLowerCase()")
	public Bird cachePut(Bird bird) {
		return bird;
	}

	public Bird findRandom(String regionCode) {
		if (regionCode == null || regionCode.isEmpty() || "WORLD".equals(regionCode)) {
			return birdRepository.findOne(birdRepository.randomId());
		}

		logger.debug("Finding random bird with region {}", regionCode);

		return birdRepository.randomBird(regionCode, new PageRequest(1, 1)).iterator().next();
	}

	public Page<Bird> findAll(Pageable pageable) {
		return birdRepository.findAll(pageable);
	}

	public Page<Bird> byRegion(String region) {
		return birdRepository.findByRegion_id(region, new PageRequest(1, 20));
	}

	// TODO @Cacheable - create cache etc
	public Bird findById(Integer birdId) {
		return birdRepository.findOne(birdId);
	}
}

package se.atrosys.birds.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import se.atrosys.birds.model.Bird;
import se.atrosys.birds.model.Region;
import se.atrosys.birds.model.RegionalScarcity;
import se.atrosys.birds.repository.BirdRepository;
import se.atrosys.birds.repository.RegionRepository;
import se.atrosys.birds.repository.RegionalScarcityRepository;

import java.util.Set;

/**
 * TODO write documentation
 */
@Component
public class BirdService {
	private final BirdRepository birdRepository;
	private final RegionRepository regionRepository;
	private final RegionalScarcityRepository regionalScarcityRepository;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public BirdService(BirdRepository birdRepository,
	                   RegionRepository regionRepository,
	                   RegionalScarcityRepository regionalScarcityRepository) {
		this.birdRepository = birdRepository;
		this.regionRepository = regionRepository;
		this.regionalScarcityRepository = regionalScarcityRepository;
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
		if ("WORLD".equals(regionCode)) {
			return birdRepository.findOne(birdRepository.randomId());
		}

		return birdRepository.findOne(birdRepository.randomId(regionCode));
	}

	public Page<Bird> findAll(Pageable pageable) {
		return birdRepository.findAll(pageable);
	}
}

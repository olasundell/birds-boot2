package se.atrosys.birds.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import se.atrosys.birds.avibase.AviBaseRegionalScarcity;
import se.atrosys.birds.avibase.AviBaseResult;
import se.atrosys.birds.model.Bird;
import se.atrosys.birds.model.Region;
import se.atrosys.birds.model.RegionalScarcity;
import se.atrosys.birds.repository.RegionRepository;
import se.atrosys.birds.repository.RegionalScarcityRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * TODO write documentation
 */
@Component
public class RegionScarcityService {
	private final BirdService birdService;
	private final RegionRepository regionRepository;
	private final RegionalScarcityRepository regionalScarcityRepository;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public RegionScarcityService(BirdService birdService,
	                             RegionRepository regionRepository,
	                             RegionalScarcityRepository regionalScarcityRepository) {

		this.birdService = birdService;
		this.regionRepository = regionRepository;
		this.regionalScarcityRepository = regionalScarcityRepository;
	}

	public void populateRegionalScarcities() {
		Map<Region, List<RegionalScarcity>> map = readRegionalScarcities();
		map.values().forEach(regionalScarcityRepository::save);
	}

	protected Map<Region, List<RegionalScarcity>> readRegionalScarcities() {
		List<Region> regions = new ArrayList<>();
		regionRepository.findAll().forEach(regions::add);

		return regions.stream().collect(Collectors.toMap(Function.identity(), this::readRegion));
	}

	private List<RegionalScarcity> readRegion(Region region) {
		logger.info("Populating birds from region {} ({}) with scarcities", region.getName(), region.getCode());
		AviBaseResult result = readRegionFile(region);

		return result.getRegionalScarcities()
//			.parallelStream()
			.stream()
			.map(r -> mapRegionalScarcity(region, r))
			.collect(Collectors.toList());
	}

	protected AviBaseResult readRegionFile(Region region) {
		final ClassPathResource classPathResource = new ClassPathResource("regions/" + region.getCode() + "-ioc.json");
		try {
			AviBaseResult read = new ObjectMapper().readValue(classPathResource.getFile(), AviBaseResult.class);
			return read;
		} catch (IOException e) {
			throw new IllegalStateException("Could not read file " + classPathResource.getPath() + "/" + classPathResource.getFilename(), e);
		}
	}

	private RegionalScarcity mapRegionalScarcity(Region region, AviBaseRegionalScarcity aviBaseRegionalScarcity) {
		Bird bird = birdService.findByScientificName(aviBaseRegionalScarcity.getScientificName());
		if (bird == null) {
			throw new IllegalArgumentException(aviBaseRegionalScarcity.getScientificName() + " has no corresponding bird in database");
		}
		if (bird.getId() == null) {
			throw new IllegalStateException("Bird with sciname " + bird.getScientificName() + " has no id");
		}

		return RegionalScarcity.builder()
			.bird(bird)
			.region(region)
			.scarcity(aviBaseRegionalScarcity.getScarcities())
			.build();
	}
}

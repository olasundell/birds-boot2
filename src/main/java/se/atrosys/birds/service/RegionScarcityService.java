package se.atrosys.birds.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import se.atrosys.birds.model.Scarcity;
import se.atrosys.birds.taxonomy.avibase.AviBaseRegionalScarcity;
import se.atrosys.birds.taxonomy.avibase.AviBaseResult;
import se.atrosys.birds.model.Bird;
import se.atrosys.birds.model.Region;
import se.atrosys.birds.model.RegionalScarcity;
import se.atrosys.birds.repository.RegionRepository;
import se.atrosys.birds.repository.RegionalScarcityRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
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
	private static final Set<String> UNFORTUNATELY_PROBABLY_EXTINCT = new HashSet<>();

	static {
		UNFORTUNATELY_PROBABLY_EXTINCT.add("Oceanodroma macrodactyla");
		UNFORTUNATELY_PROBABLY_EXTINCT.add("Pterodroma caribbaea");
		UNFORTUNATELY_PROBABLY_EXTINCT.add("Siphonorhis americana");
		UNFORTUNATELY_PROBABLY_EXTINCT.add("Psittacara maugei");
		UNFORTUNATELY_PROBABLY_EXTINCT.add("Melamprosops phaeosoma");
		UNFORTUNATELY_PROBABLY_EXTINCT.add("Psittirostra psittacea");
		UNFORTUNATELY_PROBABLY_EXTINCT.add("Anodorhynchus glaucus");
		UNFORTUNATELY_PROBABLY_EXTINCT.add("Aphanocrex podarces");
		UNFORTUNATELY_PROBABLY_EXTINCT.add("Nycticorax olsoni");
		UNFORTUNATELY_PROBABLY_EXTINCT.add("Vanellus macropterus");
		UNFORTUNATELY_PROBABLY_EXTINCT.add("Ophrysia superciliosa");
		UNFORTUNATELY_PROBABLY_EXTINCT.add("Tadorna cristata");
		UNFORTUNATELY_PROBABLY_EXTINCT.add("Rhodonessa caryophyllacea");
		UNFORTUNATELY_PROBABLY_EXTINCT.add("Alopecoenas norfolkensis");
		UNFORTUNATELY_PROBABLY_EXTINCT.add("Callaeas cinereus");
		UNFORTUNATELY_PROBABLY_EXTINCT.add("Zosterops albogularis");
		UNFORTUNATELY_PROBABLY_EXTINCT.add("Todiramphus gambieri");
		UNFORTUNATELY_PROBABLY_EXTINCT.add("Cyanoramphus subflavescens");
		UNFORTUNATELY_PROBABLY_EXTINCT.add("Cyanoramphus erythrotis");
		UNFORTUNATELY_PROBABLY_EXTINCT.add("Acrocephalus longirostris");
		UNFORTUNATELY_PROBABLY_EXTINCT.add("Gallinula pacifica");
		UNFORTUNATELY_PROBABLY_EXTINCT.add("Charmosyna diadema");
	}

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
			.parallelStream()
//			.stream()
			.filter(abr -> !abr.getScarcities().contains(Scarcity.EXTINCT))
			.filter(abr -> !UNFORTUNATELY_PROBABLY_EXTINCT.contains(abr.getScientificName()))
			.map(r -> mapRegionalScarcity(region, r))
			.filter(Objects::nonNull)
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

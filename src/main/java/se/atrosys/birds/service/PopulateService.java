package se.atrosys.birds.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import se.atrosys.birds.taxonomy.avibase.AviBaseRegionService;
import se.atrosys.birds.taxonomy.avibase.AviBaseResult;
import se.atrosys.birds.taxonomy.avibase.AviBaseService;
import se.atrosys.birds.model.Order;
import se.atrosys.birds.model.Region;
import se.atrosys.birds.repository.RegionRepository;
import se.atrosys.birds.taxonomy.ebird.EbirdService;
import se.atrosys.birds.taxonomy.ioc.IocTranslationService;
import se.atrosys.birds.taxonomy.xml.model.IocList;
import se.atrosys.birds.taxonomy.xml.service.BirdsFromXmlService;
import se.atrosys.birds.taxonomy.xml.service.IocListConverter;

import java.io.IOException;
import java.util.List;

/**
 * TODO write documentation
 */
@Component
public class PopulateService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final BirdsFromXmlService xmlService;
	private final AviBaseRegionService aviBaseRegionService;
	private final RegionRepository regionRepository;
	private final BirdPopulator birdPopulator;
	private final AviBaseService aviBaseService;
	private final RegionScarcityService regionScarcityService;
	private final EbirdService ebirdService;
	private final IocTranslationService iocTranslationService;
	private final IocListConverter iocListConverter;

	public PopulateService(BirdsFromXmlService xmlService,
	                       AviBaseRegionService aviBaseRegionService,
	                       RegionRepository regionRepository,
	                       BirdPopulator birdPopulator,
	                       AviBaseService aviBaseService,
	                       RegionScarcityService regionScarcityService,
	                       EbirdService ebirdService,
	                       IocTranslationService iocTranslationService,
	                       IocListConverter iocListConverter) {

		this.xmlService = xmlService;
		this.aviBaseRegionService = aviBaseRegionService;
		this.regionRepository = regionRepository;
		this.birdPopulator = birdPopulator;
		this.aviBaseService = aviBaseService;
		this.regionScarcityService = regionScarcityService;
		this.ebirdService = ebirdService;
		this.iocTranslationService = iocTranslationService;
		this.iocListConverter = iocListConverter;
	}

	public void populate() {
		logger.info("Populating...");
		populateRegions();
		populateBirds();
		regionScarcityService.populateRegionalScarcities();
	}

	public void populateBirds() {
		populateBirds(-1);
	}

	private void setBirdRegions(Region region) {
		try {
			AviBaseResult result = aviBaseService.getAviBaseStuff(region);
		} catch (IOException e) {
			logger.error("Could not read from avi-base", e);
		}
	}

	public void populateRegions() {
		regionRepository.save(Region.builder().code("WORLD").name("Whole world").build());
		regionRepository.save(aviBaseRegionService.readRegions());
	}

	public void populateBirds(int i) {
		IocList iocList = xmlService.readIocList();
		iocList = xmlService.readCsv(iocList);

		iocListConverter.populateLanguages(xmlService.languages(iocList));
		List<Order> orders = iocListConverter.convertIocList(iocList);

		orders = ebirdService.addEbirdInformation(orders, iocTranslationService.read());

		if (i == -1) {
			orders.forEach(birdPopulator::saveOrder);
		} else {
			orders.subList(0, i).forEach(birdPopulator::saveOrder);
		}
	}
}

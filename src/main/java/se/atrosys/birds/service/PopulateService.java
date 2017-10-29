package se.atrosys.birds.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import se.atrosys.birds.avibase.AviBaseRegionService;
import se.atrosys.birds.avibase.AviBaseResult;
import se.atrosys.birds.avibase.AviBaseService;
import se.atrosys.birds.model.Order;
import se.atrosys.birds.model.Region;
import se.atrosys.birds.repository.RegionRepository;
import se.atrosys.birds.xml.model.IocList;
import se.atrosys.birds.xml.service.BirdsFromXmlService;
import se.atrosys.birds.xml.service.IocListConverter;

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

	public PopulateService(BirdsFromXmlService xmlService,
	                       AviBaseRegionService aviBaseRegionService,
	                       RegionRepository regionRepository,
	                       BirdPopulator birdPopulator,
	                       AviBaseService aviBaseService,
	                       RegionScarcityService regionScarcityService) {

		this.xmlService = xmlService;
		this.aviBaseRegionService = aviBaseRegionService;
		this.regionRepository = regionRepository;
		this.birdPopulator = birdPopulator;
		this.aviBaseService = aviBaseService;
		this.regionScarcityService = regionScarcityService;
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
		regionRepository.save(aviBaseRegionService.readRegions());
	}

	public void populateBirds(int i) {
		IocList iocList = xmlService.readIocList();
		iocList = xmlService.readCsv(iocList);

		List<Order> orders = new IocListConverter(xmlService.languages(iocList)).convertIocList(iocList);

//		birdPopulator.saveOrder(orders.get(0));
		if (i == -1) {
			orders.forEach(birdPopulator::saveOrder);
		} else {
			orders.subList(0, i).forEach(birdPopulator::saveOrder);
		}
//		regionRepository.findAll().forEach(this::setBirdRegions);
	}
}

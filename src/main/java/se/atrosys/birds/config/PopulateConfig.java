package se.atrosys.birds.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import se.atrosys.birds.avibase.AviBaseRegionService;
import se.atrosys.birds.avibase.AviBaseResult;
import se.atrosys.birds.avibase.AviBaseService;
import se.atrosys.birds.model.Order;
import se.atrosys.birds.model.Region;
import se.atrosys.birds.repository.RegionRepository;
import se.atrosys.birds.service.BirdPopulator;
import se.atrosys.birds.xml.model.IocList;
import se.atrosys.birds.xml.service.BirdsFromXmlService;
import se.atrosys.birds.xml.service.IocListConverter;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

/**
 * TODO write documentation
 */
@Configuration
public class PopulateConfig {
	private final BirdsFromXmlService xmlService;
	private final AviBaseRegionService aviBaseRegionService;
	private final RegionRepository regionRepository;
	private final BirdPopulator birdPopulator;
	private final AviBaseService aviBaseService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public PopulateConfig(BirdsFromXmlService xmlService,
	                      AviBaseRegionService aviBaseRegionService,
	                      RegionRepository regionRepository,
	                      BirdPopulator birdPopulator,
	                      AviBaseService aviBaseService) {

		this.xmlService = xmlService;
		this.aviBaseRegionService = aviBaseRegionService;
		this.regionRepository = regionRepository;
		this.birdPopulator = birdPopulator;
		this.aviBaseService = aviBaseService;
	}

	@PostConstruct
	public void populate() {
		logger.info("Populating...");
		populateRegions();

		IocList iocList = xmlService.readIocList();
		iocList = xmlService.readCsv(iocList);

		List<Order> orders = new IocListConverter(xmlService.languages(iocList)).convertIocList(iocList);

//		birdPopulator.saveOrder(orders.get(0));
		orders.forEach(birdPopulator::saveOrder);
//		regionRepository.findAll().forEach(this::setBirdRegions);
	}

	private void setBirdRegions(Region region) {
		try {
			AviBaseResult result = aviBaseService.getAviBaseStuff(region);
		} catch (IOException e) {
			logger.error("Could not read from avi-base", e);
		}
	}

	private void populateRegions() {
		regionRepository.save(aviBaseRegionService.readRegions());
	}

}

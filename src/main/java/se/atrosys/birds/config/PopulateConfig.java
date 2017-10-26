package se.atrosys.birds.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import se.atrosys.birds.model.Bird;
import se.atrosys.birds.model.Family;
import se.atrosys.birds.model.Genus;
import se.atrosys.birds.model.Language;
import se.atrosys.birds.model.Order;
import se.atrosys.birds.repository.BirdRepository;
import se.atrosys.birds.repository.FamilyRepository;
import se.atrosys.birds.repository.GenusRepository;
import se.atrosys.birds.repository.LanguageRepository;
import se.atrosys.birds.repository.OrderRepository;
import se.atrosys.birds.xml.model.IocList;
import se.atrosys.birds.xml.service.BirdsFromXmlService;
import se.atrosys.birds.xml.service.IocListConverter;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.function.Consumer;

/**
 * TODO write documentation
 */
@Configuration
public class PopulateConfig {
	private final BirdsFromXmlService xmlService;
	private final BirdRepository birdRepository;
	private final FamilyRepository familyRepository;
	private final GenusRepository genusRepository;
	private final OrderRepository orderRepository;
	private final LanguageRepository languageRepository;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public PopulateConfig(BirdsFromXmlService xmlService,
	                      BirdRepository birdRepository,
	                      FamilyRepository familyRepository,
	                      GenusRepository genusRepository,
	                      OrderRepository orderRepository, LanguageRepository languageRepository) {

		this.xmlService = xmlService;
		this.birdRepository = birdRepository;
		this.familyRepository = familyRepository;
		this.genusRepository = genusRepository;
		this.orderRepository = orderRepository;
		this.languageRepository = languageRepository;
	}

	@PostConstruct
	public void populate() {
		logger.info("Populating...");
		IocList iocList = xmlService.readIocList();
		iocList = xmlService.readCsv(iocList);

		xmlService.languages(iocList)
			.stream()
			.map(s -> Language.builder().name(s).build())
			.forEach(languageRepository::save);

		List<Order> orders = new IocListConverter(languageRepository.findAll()).convertIocList(iocList);

		saveOrder(orders.get(0));
//		orders.forEach(this::saveOrder);
	}

	private void saveOrder(Order o) {
		logger.info("Saving order {}", o.getName());

		Order order = orderRepository.save(o);
		o.getFamilies().forEach(
			saveFamily(order)
		);
	}

	private Consumer<Family> saveFamily(Order order) {
		return f -> {
			f.setOrder(order);
			Family family = familyRepository.save(f);
			f.getGenus().forEach(
				g -> saveGenus(family, g)
			);
		};
	}

	private void saveGenus(Family family, Genus g) {
		g.setFamily(family);
		Genus genus = genusRepository.save(g);
		g.getBirds().forEach(
			saveBird(genus)
		);
	}

	private Consumer<Bird> saveBird(Genus genus) {
		return b ->{
			b.setGenus(genus);
			birdRepository.save(b);
		};
	}
}

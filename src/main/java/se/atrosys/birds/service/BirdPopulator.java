package se.atrosys.birds.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.atrosys.birds.model.Bird;
import se.atrosys.birds.model.Family;
import se.atrosys.birds.model.Genus;
import se.atrosys.birds.model.Order;
import se.atrosys.birds.repository.FamilyRepository;
import se.atrosys.birds.repository.GenusRepository;
import se.atrosys.birds.repository.LanguageRepository;
import se.atrosys.birds.repository.OrderRepository;
import se.atrosys.birds.taxonomy.ebird.EbirdService;
import se.atrosys.birds.taxonomy.ebird.EbirdSpecies;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * TODO write documentation
 */
@Component
public class BirdPopulator {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final BirdService birdService;
	private final FamilyRepository familyRepository;
	private final GenusRepository genusRepository;
	private final OrderRepository orderRepository;
	private final EbirdService ebirdService;

	@Autowired
	public BirdPopulator(BirdService birdService,
	                     FamilyRepository familyRepository,
	                     GenusRepository genusRepository,
	                     OrderRepository orderRepository,
	                     LanguageRepository languageRepository, EbirdService ebirdService) {
		this.birdService = birdService;
		this.familyRepository = familyRepository;
		this.genusRepository = genusRepository;
		this.orderRepository = orderRepository;
		this.ebirdService = ebirdService;


	}

	public void saveOrder(Order o) {
		logger.debug("Saving order {}", o.getName());

		Order order = orderRepository.save(o);
		o.getFamilies().forEach(
			saveFamily(order)
		);
	}

	private Consumer<Family> saveFamily(Order order) {
		return f -> {
			f.setOrder(order);
//			f.getGenus().forEach(
			f.getGenus().parallelStream().forEach(
				g -> saveGenus(f, g)
			);
			Family family = familyRepository.save(f);
		};
	}

	private void saveGenus(Family family, Genus g) {
		g.setFamily(family);
		Genus genus = genusRepository.save(g);
//		g.getBirds().parallelStream().forEach(
		g.getBirds().forEach(
			saveBird(genus)
		);
	}

	private Consumer<Bird> saveBird(Genus genus) {
		return b ->{
			b.setGenus(genus);
			birdService.save(b);
		};
	}
}

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

import java.util.function.Consumer;

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

	@Autowired
	public BirdPopulator(BirdService birdService,
	                     FamilyRepository familyRepository,
	                     GenusRepository genusRepository,
	                     OrderRepository orderRepository,
	                     LanguageRepository languageRepository) {
		this.birdService = birdService;
		this.familyRepository = familyRepository;
		this.genusRepository = genusRepository;
		this.orderRepository = orderRepository;
	}

	public void saveOrder(Order o) {
		logger.trace("Saving order {}", o.getName());

		Order order = orderRepository.save(o);
		o.getFamilies().forEach(
			saveFamily(order)
		);
	}

	private Consumer<Family> saveFamily(Order order) {
		return f -> {
			f.setOrder(order);
			f.getGenus().forEach(
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

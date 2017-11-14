package se.atrosys.birds.taxonomy.xml.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.atrosys.birds.model.Bird;
import se.atrosys.birds.model.Family;
import se.atrosys.birds.model.Genus;
import se.atrosys.birds.model.Language;
import se.atrosys.birds.model.Order;
import se.atrosys.birds.repository.LanguageRepository;
import se.atrosys.birds.taxonomy.xml.model.IocList;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * TODO write documentation
 */
public class IocListConverterTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private IocListConverter iocListConverter;
	private IocList iocList;
	private Set<String> languages;

	@Before
	public void setUp() throws Exception {
		final BirdsFromXmlService birdsFromXmlService = new BirdsFromXmlService();
		iocList = birdsFromXmlService.readIocList();
		iocList = birdsFromXmlService.readCsv(iocList);

		languages = birdsFromXmlService.languages(iocList);

//		final List<Language> list = languages.stream()
//			.map(l -> Language.builder()
//			.name(l)
//			.build()).collect(Collectors.toList());

		Assert.assertFalse("Should have non-empty language list", languages.isEmpty());

		final LanguageRepository languageRepository = mockLangRepo();

		iocListConverter = new IocListConverter(new LanguageConverter(languageRepository));
		iocListConverter.populateLanguages(languages);
	}

	private LanguageRepository mockLangRepo() {
		final LanguageRepository languageRepository = Mockito.mock(LanguageRepository.class);
		final AtomicInteger integer = new AtomicInteger(0);

		Mockito.when(languageRepository.save(Mockito.any(Language.class))).thenAnswer(invocation -> {
			Language language = invocation.getArgumentAt(0, Language.class);
			language.setId(integer.getAndIncrement());
			return language;
		});
		return languageRepository;
	}

	@Test
	public void shouldConvertIocList() {
		Assert.assertNotNull(iocList);

		List<Order> orders = iocListConverter.convertIocList(iocList);

		Assert.assertNotNull("Order list is null", orders);
		Assert.assertFalse("Order list is empty", orders.isEmpty());

		orders.forEach(this::assertOrder);
	}

	private void assertOrder(Order order) {
		Assert.assertNotNull("Order has no families", order.getFamilies());
		Assert.assertNotNull("Order has no latin name", order.getName());

		order.getFamilies().forEach(this::assertFamily);
	}

	private void assertFamily(Family family) {
		Assert.assertNotNull("Family has no latin name", family.getName());
//		Assert.assertNotNull("Family has no english name", family.getEnglishName());
		Assert.assertNotNull("Family has no genus", family.getGenus());
		Assert.assertFalse("Family should have genuses", family.getGenus().isEmpty());

		family.getGenus().forEach(genus -> {
			genus.setFamily(family);
			assertGenus(genus);
		});

	}

	private void assertGenus(Genus genus) {
		Assert.assertNotNull("Genus should have latin name", genus.getName());
		Assert.assertNotNull("Genus " + genus.getName() + "  should have birds", genus.getBirds());
		// some genii only have extinct birds
//		Assert.assertFalse("Genus " + genus.getName() + " should have several birds", genus.getBirds().isEmpty());

		genus.getBirds().forEach(bird -> {
			bird.setGenus(genus);
			assertSpecies(bird);
		});
	}

	private void assertSpecies(Bird bird) {
		Assert.assertNotNull("Species should have breeding regions", bird.getBreedingRegions());
		Assert.assertFalse("Species should have non-empty breeding regions list", bird.getBreedingRegions().isEmpty());

//		Assert.assertNotNull("Species should have breeding subregions", bird.getBreedingSubregions());

		Assert.assertNotNull("Species should have latin name", bird.getScientificName());
		Assert.assertFalse("Species should have latin name", bird.getScientificName().isEmpty());
		final String name = bird.getGenusName() + " " + bird.getScientificName();

		Assert.assertNotNull("Species should have non-null names", bird.getBirdNames());

		Assert.assertFalse("Species " + name + " should have non-empty names list", bird.getBirdNames().isEmpty());

		logger.info("Asserting " + name);
		bird.getBirdNames().forEach(l -> l.setBird(bird));
		Assert.assertTrue("Should contain an english name", bird.namesByLanguage().containsKey("english"));
	}


	@Test
	public void localeStuff() {
		final Locale locale = Locale.ENGLISH;

		Assert.assertNotNull(locale);
	}
}
package se.atrosys.birds.xml.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import se.atrosys.birds.xml.model.IocList;
import se.atrosys.birds.xml.model.XmlFamily;
import se.atrosys.birds.xml.model.XmlGenus;
import se.atrosys.birds.xml.model.XmlOrder;
import se.atrosys.birds.xml.model.XmlSpecies;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * TODO write documentation
 */
public class BirdsFromXmlServiceTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private BirdsFromXmlService birdsFromXmlService;

	@Before
	public void setUp() throws Exception {
		birdsFromXmlService = new BirdsFromXmlService();
	}

	@Test
	public void shouldReadCsv() throws IOException {
		birdsFromXmlService.readCsv(birdsFromXmlService.readIocList());
//		Assert.assertNotNull(result);
	}

	@Test
	public void shouldReadIocList() {
		StopWatch unmarshallWatch = new StopWatch("unmarshall");
		StopWatch assertWatch = new StopWatch("assert");

		unmarshallWatch.start();
		IocList result = birdsFromXmlService.readIocList();
		unmarshallWatch.stop();

		assertWatch.start();

		Assert.assertNotNull(result);

		final List<XmlOrder> orders = result.getOrders();
		Assert.assertNotNull("Order list is null", orders);
		Assert.assertFalse("Order list is empty", orders.isEmpty());

		orders.forEach(this::assertOrder);
		assertWatch.stop();

		logger.info("Test OK, unmarshalling: {} ms asserting: {} ms",
			unmarshallWatch.getLastTaskTimeMillis(),
			assertWatch.getLastTaskTimeMillis());
	}

	private void assertOrder(XmlOrder order) {
		Assert.assertNotNull("Order " + order.toString() + " has no code", order.getCode());
		Assert.assertNotNull("Order has no families", order.getFamilies());
		Assert.assertNotNull("Order has no latin name", order.getLatinName());
		Assert.assertNotNull("Order has no note", order.getNote());

		order.getFamilies().forEach(this::assertFamily);
	}

	private void assertFamily(XmlFamily family) {
		Assert.assertNotNull("Family has no latin name", family.getLatinName());
		Assert.assertNotNull("Family has no english name", family.getEnglishName());
		Assert.assertNotNull("Family has no genus", family.getGenus());
		Assert.assertFalse("Family should have genuses", family.getGenus().isEmpty());
//		assertGenus(family.getGenus().get(0));
		family.getGenus().forEach(this::assertGenus);

	}

	private void assertGenus(XmlGenus genus) {
		Assert.assertNotNull("Genus should have authority", genus.getAuthority());
		Assert.assertNotNull("Genus should have latin name", genus.getLatinName());
		Assert.assertNotNull("Genus should have species", genus.getSpecies());
		Assert.assertFalse("Genus should have several species", genus.getSpecies().isEmpty());

//		assertSpecies(genus.getSpecies().get(0));
		genus.getSpecies().forEach(this::assertSpecies);
	}

	private void assertSpecies(XmlSpecies species) {
		Assert.assertNotNull("Species should have authority", species.getAuthority());
		Assert.assertNotNull("Species should have breeding regions", species.getBreedingRegions());
		Assert.assertNotNull("Species should have breeding subregions", species.getBreedingSubregions());

		Assert.assertNotNull("Species should have english name", species.getEnglishName());
		Assert.assertFalse("Species should have english name", species.getEnglishName().isEmpty());

		Assert.assertNotNull("Species should have latin name", species.getLatinName());
		Assert.assertFalse("Species should have latin name", species.getLatinName().isEmpty());
	}
}
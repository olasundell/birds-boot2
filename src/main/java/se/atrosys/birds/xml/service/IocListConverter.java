package se.atrosys.birds.xml.service;

import se.atrosys.birds.model.Bird;
import se.atrosys.birds.model.BirdName;
import se.atrosys.birds.model.BreedingRegion;
import se.atrosys.birds.model.Family;
import se.atrosys.birds.model.Genus;
import se.atrosys.birds.model.Language;
import se.atrosys.birds.model.Order;
import se.atrosys.birds.xml.model.IocList;
import se.atrosys.birds.xml.model.XmlFamily;
import se.atrosys.birds.xml.model.XmlGenus;
import se.atrosys.birds.xml.model.XmlOrder;
import se.atrosys.birds.xml.model.XmlSpecies;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * TODO write documentation
 */
public class IocListConverter {
	private final Map<String, Locale> languages;

	public IocListConverter(Collection<String> lang) {
		languages = LanguageConverter.shouldFindLanguagesAsLocales(lang);
	}

	public List<Order> convertIocList(IocList iocList) {
		return iocList.getOrders()
			.stream()
			.map(this::convertOrder)
			.collect(Collectors.toList());
	}

	private Order convertOrder(XmlOrder xmlOrder) {
		return Order.builder()
			.name(xmlOrder.getLatinName())
			.families(xmlOrder.getFamilies().stream().map(this::convertFamily).collect(Collectors.toList()))
			.build();
	}

	private Family convertFamily(XmlFamily xmlFamily) {
		return Family.builder()
			.name(xmlFamily.getLatinName())
			.genus(xmlFamily.getGenus().stream().map(this::convertGenus).collect(Collectors.toList()))
			.build();
	}

	private Genus convertGenus(XmlGenus xmlGenus) {
		return Genus.builder()
			.name(xmlGenus.getLatinName())
			.birds(xmlGenus.getSpecies().stream().map(s -> convertBird(xmlGenus, s)).collect(Collectors.toList()))
			.build();
	}

	private Bird convertBird(XmlGenus xmlGenus, XmlSpecies species) {
		return Bird.builder()
			.scientificName(xmlGenus.getLatinName().toLowerCase() + " " + species.getLatinName())
			.breedingRegions(findBreedingRegions(species))
			.birdNames(species.getNames().entrySet().stream().map(this::convertBirdName).collect(Collectors.toList()))
			.build();
	}

	private List<BreedingRegion> findBreedingRegions(XmlSpecies species) {
		return Arrays.stream(species.getBreedingRegions().split(","))
			.map(BreedingRegion::findByCode)
			.collect(Collectors.toList());
	}

	private BirdName convertBirdName(Map.Entry<String, String> nameEntry) {
		return BirdName.builder()
			.language(languages.get(nameEntry.getKey()))
			.name(nameEntry.getValue())
			.build();
	}
}

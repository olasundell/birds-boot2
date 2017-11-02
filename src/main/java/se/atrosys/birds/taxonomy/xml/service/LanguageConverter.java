package se.atrosys.birds.taxonomy.xml.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * TODO write documentation
 */
public class LanguageConverter {
	private static final Logger logger = LoggerFactory.getLogger(LanguageConverter.class);

	public static Map<String, Locale> shouldFindLanguagesAsLocales(Collection<String> languages) {
		final List<String> strings = new ArrayList<>(languages);

		return strings.stream()
			.map(LanguageConverter::mapLocale)
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	private static Map.Entry<String, Locale> mapLocale(String s) {
		return new AbstractMap.SimpleEntry<>(s, findLocale(s));
	}

	private static Locale findLocale(String lang) {
		logger.info("Finding locale for {}", lang);
		switch (lang) {
			case "Scientific Name":
				return Locale.forLanguageTag("la");
			case "Afrikaans":
				return Locale.forLanguageTag("af");
			case "Chinese (Traditional)":
				return Locale.TRADITIONAL_CHINESE;
			default:
				for (Locale l: Locale.getAvailableLocales()) {
					if (l.getDisplayLanguage().equalsIgnoreCase(lang)) {
						return l;
					}
				}
		}

		logger.error("Could not find locale for {}", lang);

		return null;
	}
}

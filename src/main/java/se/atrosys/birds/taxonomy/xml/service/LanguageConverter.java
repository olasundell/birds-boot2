package se.atrosys.birds.taxonomy.xml.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import se.atrosys.birds.model.Language;
import se.atrosys.birds.repository.LanguageRepository;

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
@Component
public class LanguageConverter {
	private static final Logger logger = LoggerFactory.getLogger(LanguageConverter.class);
	private final LanguageRepository languageRepository;

	public LanguageConverter(LanguageRepository languageRepository) {
		this.languageRepository = languageRepository;
	}

	public Map<String, Language> findAndPopulateLanguages(Collection<String> languages) {
		final List<String> strings = new ArrayList<>(languages);

		return strings.stream()
			.map(this::mapLocale)
			.collect(Collectors.toMap(Map.Entry::getKey, e -> languageRepository.save(e.getValue())));
	}

	private Map.Entry<String, Language> mapLocale(String s) {
		return new AbstractMap.SimpleEntry<>(s, Language.builder().locale(findLocale(s)).build());
	}

	private Locale findLocale(String lang) {
		logger.trace("Finding locale for {}", lang);
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

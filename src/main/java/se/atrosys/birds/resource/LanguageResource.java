package se.atrosys.birds.resource;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import se.atrosys.birds.model.Language;
import se.atrosys.birds.repository.BirdNameRepository;
import se.atrosys.birds.repository.LanguageRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * TODO write documentation
 */
@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"} )
public class LanguageResource {
	private final BirdNameRepository birdNameRepository;
	private final LanguageRepository languageRepository;

	public LanguageResource(BirdNameRepository birdNameRepository,
	                        LanguageRepository languageRepository) {
		this.birdNameRepository = birdNameRepository;
		this.languageRepository = languageRepository;
	}

	@GetMapping("/languages")
	public List<LanguageResponse> languages() {
		Iterable<Language> distinctLanguages = languageRepository.findAll();
		List<Language> languages = new ArrayList<>();
		distinctLanguages.forEach(languages::add);
		languages.sort(Comparator.comparing(l2 -> l2.getLocale().toString()));
//		return StreamSupport.stream(distinctLanguages.spliterator(), false)
		return languages.stream()
			.distinct()
			.map(l -> createResponse(l.getLocale()))
			.collect(Collectors.toList());
	}

	private LanguageResponse createResponse(Locale l) {
		return LanguageResponse.builder()
			.code(l.toString())
			.name(getDisplayName(l))
			.build();
	}

	private String getDisplayName(Locale l) {
		if (l.toString().startsWith("zh")) {
			return l.getDisplayName();
		} else {
			return l.getDisplayLanguage();
		}
	}

	@Data
	@Builder
	static class LanguageResponse {
		private String code;
		private String name;
	}
}

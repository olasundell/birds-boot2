package se.atrosys.birds.resource;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import se.atrosys.birds.model.Language;
import se.atrosys.birds.repository.BirdNameRepository;
import se.atrosys.birds.repository.LanguageRepository;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * TODO write documentation
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
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
		return StreamSupport.stream(distinctLanguages.spliterator(), false)
			.map(l -> createResponse(l.getLocale()))
			.collect(Collectors.toList());
	}

	private LanguageResponse createResponse(Locale l) {
		return LanguageResponse.builder()
			.code(l.getLanguage())
			.name(l.getDisplayLanguage())
			.build();
	}

	@Data
	@Builder
	static class LanguageResponse {
		private String code;
		private String name;
	}
}
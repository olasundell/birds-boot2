package se.atrosys.birds.resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.atrosys.birds.model.Family;
import se.atrosys.birds.repository.FamilyRepository;


/**
 * TODO write documentation
 */
@RestController
@RequestMapping(path = "/families")
public class FamilyResource {
	private final FamilyRepository familyRepository;

	public FamilyResource(FamilyRepository familyRepository) {
		this.familyRepository = familyRepository;
	}

	@GetMapping
	public Page<Family> all(Pageable pageable) {
		final Page<Family> all = familyRepository.findAll(pageable);
		return all;
	}
}

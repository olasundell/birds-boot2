package se.atrosys.birds.resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.atrosys.birds.model.Genus;
import se.atrosys.birds.repository.GenusRepository;

/**
 * TODO write documentation
 *
 */
@RestController
@RequestMapping("/genus")
public class GenusResource {
	private final GenusRepository genusRepository;

	public GenusResource(GenusRepository genusRepository) {
		this.genusRepository = genusRepository;
	}

	@GetMapping
	public Page<Genus> all(Pageable pageable) {
		return genusRepository.findAll(pageable);
	}

	@GetMapping("/{name}")
	public Genus one(@PathVariable String name) {
		return genusRepository.findFirstByName(name);
	}
}

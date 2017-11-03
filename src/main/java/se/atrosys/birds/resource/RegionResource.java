package se.atrosys.birds.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import se.atrosys.birds.model.Region;
import se.atrosys.birds.repository.RegionRepository;

import java.util.List;

/**
 * TODO write documentation
 */
@RestController
public class RegionResource {
	private final RegionRepository regionRepository;

	public RegionResource(RegionRepository regionRepository) {
		this.regionRepository = regionRepository;
	}

	@GetMapping("/regions")
	public Iterable<Region> regions() {
		return regionRepository.findAll();
	}
}

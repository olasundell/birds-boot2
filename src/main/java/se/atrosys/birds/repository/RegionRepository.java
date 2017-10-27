package se.atrosys.birds.repository;

import org.springframework.data.repository.CrudRepository;
import se.atrosys.birds.model.Region;

/**
 * TODO write documentation
 */
public interface RegionRepository extends CrudRepository<Region, Integer> {
	Region findByName(String name);
	Region findByCode(String code);
}

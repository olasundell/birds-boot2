package se.atrosys.birds.repository;

import org.springframework.data.repository.CrudRepository;
import se.atrosys.birds.model.Bird;
import se.atrosys.birds.model.Region;
import se.atrosys.birds.model.RegionalScarcity;

import java.util.Set;

/**
 * TODO write documentation
 */
public interface RegionalScarcityRepository extends CrudRepository<RegionalScarcity, Integer> {
	RegionalScarcity findByBirdAndRegion(Bird bird, Region region);
	Set<RegionalScarcity> findByBird(Bird bird);
	Set<RegionalScarcity> findByRegion(Region region);
}

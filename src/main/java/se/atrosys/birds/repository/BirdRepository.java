package se.atrosys.birds.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import se.atrosys.birds.model.Bird;
import se.atrosys.birds.model.Region;

/**
 * TODO write documentation
 */
public interface BirdRepository extends PagingAndSortingRepository<Bird, Integer> {
	@Query(value="SELECT id FROM Birds ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
	Integer randomId();

	@Query(value = "SELECT birds.id from birds, region, regional_scarcity as rs " +
		"where region.code = :#{#regionCode} and rs.region_id = region.id and rs.bird_id = birds.id " +
		"ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
	Integer randomId(@Param("regionCode") String regionCode);

	Bird findByScientificName(String scientificName);
}

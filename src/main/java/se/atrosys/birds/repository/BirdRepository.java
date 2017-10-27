package se.atrosys.birds.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import se.atrosys.birds.model.Bird;

/**
 * TODO write documentation
 */
public interface BirdRepository extends PagingAndSortingRepository<Bird, Integer> {
	@Query(value="SELECT id FROM Bird ORDER BY RAND() LIMIT 1", nativeQuery = true)
	Integer randomId();

	Bird findByScientificName(String scientificName);
}

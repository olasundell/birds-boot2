package se.atrosys.birds.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import se.atrosys.birds.model.Family;

/**
 * TODO write documentation
 */
public interface FamilyRepository extends PagingAndSortingRepository<Family, Integer> {
	Family findFirstByName(String name);
}

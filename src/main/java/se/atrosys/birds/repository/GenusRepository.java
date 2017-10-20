package se.atrosys.birds.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import se.atrosys.birds.model.Genus;

/**
 * TODO write documentation
 */
public interface GenusRepository extends PagingAndSortingRepository<Genus, Integer> {
	Genus findFirstByName(String name);
}

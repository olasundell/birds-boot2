package se.atrosys.birds.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import se.atrosys.birds.model.Bird;

/**
 * TODO write documentation
 */
public interface BirdRepository extends PagingAndSortingRepository<Bird, Integer> {
}

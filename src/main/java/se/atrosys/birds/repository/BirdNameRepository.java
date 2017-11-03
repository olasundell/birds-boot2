package se.atrosys.birds.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import se.atrosys.birds.model.BirdName;

import java.util.Locale;

/**
 * TODO write documentation
 */
public interface BirdNameRepository extends PagingAndSortingRepository<BirdName, Integer> {
}

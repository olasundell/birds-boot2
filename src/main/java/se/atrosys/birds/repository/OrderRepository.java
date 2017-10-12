package se.atrosys.birds.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import se.atrosys.birds.model.Order;

/**
 * TODO write documentation
 */
public interface OrderRepository extends PagingAndSortingRepository<Order, Integer> {
}

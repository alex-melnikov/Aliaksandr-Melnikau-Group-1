package org.shop.repository.map;

import java.util.List;

import org.apache.commons.collections.Predicate;
import org.shop.data.Order;
import org.shop.repository.OrderRepository;
import org.springframework.stereotype.Repository;

/**
 * The Class OrderMapRepository.
 * 
 * @author Dzmitry_Naskou
 */
@Repository
public class OrderMapRepository extends AbstractMapRepository<Order> implements
		OrderRepository {

	/**
	 * Instantiates a new order map based repository.
	 * 
	 * @param initialSequence
	 *            the initial sequence
	 */
	public OrderMapRepository(long initialSequence) {
		super(initialSequence);
	}

	public Order getOrderById(Long id) {
		return get(id);
	}

	public Long createOrder(Order order) {
		return create(order);
	}

	public void updateOrder(Order order) {
		update(order);
	}

	public List<Order> getOrdersByUserId(Long userId) {
		return select(new OrderByUserPredicate(userId));
	}

	/**
	 * The Class OrderByUserPredicate.
	 */
	private class OrderByUserPredicate implements Predicate {

		/** The user id. */
		private Long userId;

		/**
		 * Instantiates a new order by user predicate.
		 * 
		 * @param userId
		 *            the user id
		 */
		private OrderByUserPredicate(Long userId) {
			super();
			this.userId = userId;
		}

		public boolean evaluate(Object input) {
			if (input instanceof Order) {
				Order order = (Order) input;

				return userId.equals(order.getUser().getId());
			}

			return false;
		}
	}
}

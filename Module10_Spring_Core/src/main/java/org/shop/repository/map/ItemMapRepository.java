package org.shop.repository.map;

import java.util.List;

import org.apache.commons.collections.Predicate;
import org.shop.data.Item;
import org.shop.repository.ItemRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ItemMapRepository extends AbstractMapRepository<Item> implements
		ItemRepository {

	public Long createItem(Item item) {
		return create(item);
	}

	public void deleteItem(Long itemId) {
		deleteItem(itemId);
	}

	public List<Item> getItemsByOrderId(Long orderId) {
		return select(new ItemByOrderPredicate(orderId));
	}

	private class ItemByOrderPredicate implements Predicate {

		private Long orderId;

		private ItemByOrderPredicate(Long orderId) {
			super();
			this.orderId = orderId;
		}

		public boolean evaluate(Object input) {
			if (input instanceof Item) {
				Item item = (Item) input;

				return orderId.equals(item.getOrder().getId());
			}

			return false;
		}
	}
}

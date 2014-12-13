package org.shop.api.impl;

import java.util.List;

import org.shop.api.ItemService;
import org.shop.data.Item;
import org.shop.repository.ItemRepository;

public class ItemServiceImpl implements ItemService {
    
    /** The item repository. */
    private final ItemRepository repository;

    /**
     * Instantiates a new item service impl.
     *
     * @param repository the item repository
     */
    public ItemServiceImpl(ItemRepository repository) {
        super();
        this.repository = repository;
    }

    public Long createItem(Item item) {
        return repository.createItem(item);
    }

    public void deleteItem(Long itemId) {
        repository.deleteItem(itemId);
    }

    public List<Item> getItemsByOrderId(Long orderId) {
        return repository.getItemsByOrderId(orderId);
    }
}

package org.shop.repository.map;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.Predicate;
import org.shop.data.Product;
import org.shop.repository.ProductRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ProductMapRepository extends AbstractMapRepository<Product>
		implements ProductRepository {

	public Product getProductById(Long productId) {
		return get(productId);
	}

	public List<Product> getProducts() {
		return new ArrayList<Product>(register.values());
	}

	public List<Product> getProductsByName(String name) {
		return select(new ProductByNamePredicate(name));
	}

	public Long createProduct(Product product) {
		return create(product);
	}

	public void updateProduct(Product product) {
		update(product);
	}

	public void deleteProduct(Long productId) {
		delete(productId);
	}

	private class ProductByNamePredicate implements Predicate {
		private String name;

		private ProductByNamePredicate(String name) {
			super();
			this.name = name;
		}

		public boolean evaluate(Object input) {
			if (input instanceof Product) {
				Product product = (Product) input;

				return name.equalsIgnoreCase(product.getName());
			}

			return false;
		}
	}
}

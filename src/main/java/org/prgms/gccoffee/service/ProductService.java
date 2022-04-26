package org.prgms.gccoffee.service;

import java.util.List;

import org.prgms.gccoffee.model.Category;
import org.prgms.gccoffee.model.Product;

public interface ProductService {
	List<Product> getProductByCategory(Category category);

	List<Product> getAllProducts();

	Product createProduct(String productName, Category category, long price);

	Product createProduct(String productName, Category category, long price, String description);
}

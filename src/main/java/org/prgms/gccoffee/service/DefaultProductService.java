package org.prgms.gccoffee.service;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.prgms.gccoffee.model.Category;
import org.prgms.gccoffee.model.Product;
import org.prgms.gccoffee.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class DefaultProductService implements ProductService {

	private final ProductRepository productRepository;

	public DefaultProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public List<Product> getProductByCategory(Category category) {
		return productRepository.findByCategory(category);
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Product createProduct(String productName, Category category, long price) {
		return productRepository.insert(new Product(UUID.randomUUID(), productName, category, price));
	}

	@Override
	public Product createProduct(String productName, Category category, long price, String description) {
		return productRepository.insert(new Product(UUID.randomUUID(), productName, category, price, description,
			LocalDateTime.now(), LocalDateTime.now()));
	}
}

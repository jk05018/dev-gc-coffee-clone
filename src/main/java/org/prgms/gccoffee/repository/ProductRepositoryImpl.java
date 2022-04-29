package org.prgms.gccoffee.repository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.prgms.gccoffee.Utils;
import org.prgms.gccoffee.model.Category;
import org.prgms.gccoffee.model.Product;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public ProductRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Product> findAll() {
		return jdbcTemplate.query("select * from products", productRowMapper());
	}

	@Override
	public Product insert(Product product) {
		final int updatedRow = jdbcTemplate.update(
			"insert into products (product_id, product_name, category, price, description, created_at, updated_at) values (UUID_TO_BIN(:productId),:productName,:category, :price, :description, :createdAt,:updatedAt)"
			, toParamMap(product));
		if (updatedRow != 1) {
			throw new RuntimeException("Nothing was inserted");
		}
		return product;
	}

	@Override
	public Product update(Product product) {
		final int update = jdbcTemplate.update(
			"update products set product_name = :productName, category = :category, price = :price, description  = :description,"
				+ " created_at = :createdAt , updated_at = :updatedAt"
				+ " where product_id = UUID_TO_BIN(:productId)"
			, toParamMap(product));

		if (update != 1) {
			throw new RuntimeException("Nothing was updated");
		}
		return product;
	}

	@Override
	public Optional<Product> findById(UUID productId) {
		try {
			return Optional.of(
				jdbcTemplate.queryForObject("select * from products where product_id = UUID_TO_BIN(:productId)",
					Collections.singletonMap("productId", productId.toString().getBytes()), productRowMapper()));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public Optional<Product> findByName(String productName) {
		try {
			return Optional.of(
				jdbcTemplate.queryForObject("select * from products where product_name = :productName",
					Collections.singletonMap("productName", productName), productRowMapper()));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<Product> findByCategory(Category category) {
		return jdbcTemplate.query("select * from products where category = :category",
			Collections.singletonMap("category", category.toString()), productRowMapper());
	}

	@Override
	public void deleteAll() {
		jdbcTemplate.update("delete from products", Collections.emptyMap());

	}

	private static final RowMapper<Product> productRowMapper() {
		return (rs, rowNum) -> {
			final UUID productId = Utils.toUUID(rs.getBytes("product_id"));
			final String productName = rs.getString("product_name");
			final Category category = Category.valueOf(rs.getString("category"));
			final int price = rs.getInt("price");
			final String description = rs.getString("description");
			final LocalDateTime createdAt = Utils.toLocalDateTime(rs.getTimestamp("created_at"));
			final LocalDateTime updatedAt = Utils.toLocalDateTime(rs.getTimestamp("updated_at"));

			return new Product(productId, productName, category, price, description, createdAt, updatedAt);
		};
	}

	private Map<String, Object> toParamMap(Product product) {
		final HashMap<String, Object> paramMap = new HashMap<>();
		paramMap.put("productId", product.getProductId().toString().getBytes());
		paramMap.put("productName", product.getProductName());
		paramMap.put("category", product.getCategory().toString());
		paramMap.put("price", product.getPrice());
		paramMap.put("description", product.getDescription());
		paramMap.put("createdAt", product.getCreatedAt());
		paramMap.put("updatedAt", product.getUpdatedAt());

		return paramMap;
	}

}

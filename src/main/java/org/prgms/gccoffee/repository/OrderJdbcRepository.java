package org.prgms.gccoffee.repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.prgms.gccoffee.model.Order;
import org.prgms.gccoffee.model.OrderItem;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class OrderJdbcRepository implements OrderRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public OrderJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	@Transactional
	public Order insert(Order order) {
		final int update = jdbcTemplate.update(
			"insert into orders(order_id ,email ,address ,postcode ,order_status ,created_at ,updated_at) " +
				"values (UUID_TO_BIN(:orderId) ,:email ,:address ,:postcode ,:orderStatus ,:createdAt ,:updatedAt)",
			toOrderParamMap(order));

		// order를 추가하고 order의 Item만큼 추가해야 한다.
		order.getOrderItems()
			.forEach(item -> {
				jdbcTemplate.update(
					"INSERT INTO order_items(order_id,product_id,category,price,quantity,created_at,updated_at) " +
						"values (UUID_TO_BIN(:orderId),UUID_TO_BIN(:productId),:category,:price,:quantity,:createdAt,:updatedAt) ",
					toOrderItemParamMap(order.getOrderId(), order.getCreatedAt(), order.getUpdatedAt(), item));
			});

		if (update != 1) {
			throw new RuntimeException("update가 되지 않았습니다");
		}
		return order;
	}

	private Map<String, Object> toOrderParamMap(Order order) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("orderId", order.getOrderId().toString().getBytes());
		paramMap.put("email", order.getEmail().getAddress());
		paramMap.put("address", order.getAddress());
		paramMap.put("postcode", order.getPostcode());
		paramMap.put("orderStatus", order.getOrderStatus().toString());
		paramMap.put("createdAt", order.getCreatedAt());
		paramMap.put("updatedAt", order.getUpdatedAt());

		return paramMap;
	}

	private Map<String, Object> toOrderItemParamMap(UUID orderId, LocalDateTime createdAt, LocalDateTime updatedAt,
		OrderItem orderItem) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("orderId", orderId.toString().getBytes());
		paramMap.put("productId", orderItem.getProductId().toString().getBytes());
		paramMap.put("category", orderItem.getCategory().toString());
		paramMap.put("price", orderItem.getPrice());
		paramMap.put("quantity", orderItem.getQuantity());
		paramMap.put("createdAt", createdAt);
		paramMap.put("updatedAt", updatedAt);

		return paramMap;
	}
}

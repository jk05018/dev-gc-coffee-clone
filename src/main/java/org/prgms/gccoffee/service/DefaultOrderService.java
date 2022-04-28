package org.prgms.gccoffee.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.prgms.gccoffee.model.Email;
import org.prgms.gccoffee.model.Order;
import org.prgms.gccoffee.model.OrderItem;
import org.prgms.gccoffee.model.OrderStatus;
import org.prgms.gccoffee.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class DefaultOrderService implements OrderService {

	private final OrderRepository orderRepository;

	public DefaultOrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Override
	public Order createOrder(Email email, String address, String postcode, List<OrderItem> orderItems) {
		final Order order = new Order(UUID.randomUUID()
			, email
			, address
			, postcode
			, orderItems
			, OrderStatus.ACCEPTED,
			LocalDateTime.now(),
			LocalDateTime.now());
		return orderRepository.insert(order);
	}
}

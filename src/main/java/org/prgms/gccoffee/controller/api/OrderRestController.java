package org.prgms.gccoffee.controller.api;

import org.prgms.gccoffee.controller.CreateOrderRequest;
import org.prgms.gccoffee.model.Email;
import org.prgms.gccoffee.model.Order;
import org.prgms.gccoffee.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderRestController {

	private final OrderService orderService;

	public OrderRestController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping("/api/v1/orders")
	public Order createOrder(@RequestBody CreateOrderRequest orderRequest){
		System.out.println("orderItems : " + orderRequest.getOrderItems());
		return orderService.createOrder(new Email(orderRequest.getEmail()), orderRequest.getAddress()
			, orderRequest.getPostcode(), orderRequest.getOrderItems());

	}
}

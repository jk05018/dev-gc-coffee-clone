package org.prgms.gccoffee.controller;

import java.util.List;
import java.util.Objects;

import org.prgms.gccoffee.model.OrderItem;

public final class CreateOrderRequest {
	private final String email;
	private final String address;
	private final String postcode;
	private final List<OrderItem> orderItems;

	public CreateOrderRequest(String email, String address, String postcode,
		List<OrderItem> orderItems) {
		this.email = email;
		this.address = address;
		this.postcode = postcode;
		this.orderItems = orderItems;
	}

	public String getEmail() {
		return email;
	}

	public String getAddress() {
		return address;
	}

	public String getPostcode() {
		return postcode;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		CreateOrderRequest that = (CreateOrderRequest)o;
		return Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getAddress(),
			that.getAddress()) && Objects.equals(getPostcode(), that.getPostcode()) && Objects.equals(
			getOrderItems(), that.getOrderItems());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getEmail(), getAddress(), getPostcode(), getOrderItems());
	}
}

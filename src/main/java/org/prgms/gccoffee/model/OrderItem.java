package org.prgms.gccoffee.model;

import java.util.Objects;
import java.util.UUID;

public final class OrderItem {
	private final UUID productId;
	private final Category category;
	private final long price;
	private final int quantity;

	public OrderItem(UUID productId, Category category, long price, int quantity) {
		this.productId = productId;
		this.category = category;
		this.price = price;
		this.quantity = quantity;
	}

	public UUID getProductId() {
		return productId;
	}

	public Category getCategory() {
		return category;
	}

	public long getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		OrderItem orderItem = (OrderItem)o;
		return getPrice() == orderItem.getPrice() && getQuantity() == orderItem.getQuantity() && Objects.equals(
			getProductId(), orderItem.getProductId()) && getCategory() == orderItem.getCategory();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getProductId(), getCategory(), getPrice(), getQuantity());
	}
}

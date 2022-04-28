package org.prgms.gccoffee.service;

import java.util.List;

import org.prgms.gccoffee.model.Email;
import org.prgms.gccoffee.model.Order;
import org.prgms.gccoffee.model.OrderItem;

public interface OrderService {

	Order createOrder(Email email, String address, String postcode, List<OrderItem> orderItems);
}

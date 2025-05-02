package ru.pizzahut.pizzamaker.service.api;

import ru.pizzahut.pizzamaker.controller.payload.OrderPayload;
import ru.pizzahut.pizzamaker.model.order.Order;

import java.util.UUID;

public interface OrderingService {

    void save(OrderPayload payload) throws CloneNotSupportedException;

    Iterable<Order> getAllOrders();

    Iterable<Order> getFilteredByStatusOrders(String orderStatus);

    Iterable<Order> getFilteredByDateOrders(String date);

    Iterable<Order> getFilteredOrders(String status, String date);

    void deleteOrderById(UUID id);

    void updateOrder(UUID id, OrderPayload payload);
}

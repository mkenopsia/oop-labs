package ru.pizzahut.pizzamaker.service.api;

import ru.pizzahut.pizzamaker.controller.payload.OrderPayload;
import ru.pizzahut.pizzamaker.model.Ingredient;
import ru.pizzahut.pizzamaker.model.order.Order;
import ru.pizzahut.pizzamaker.model.pizzaForOrder.PizzaForOrder;

public interface OrderingService {

    void save(OrderPayload payload) throws CloneNotSupportedException;

    Iterable<Order> getAllOrders();

}

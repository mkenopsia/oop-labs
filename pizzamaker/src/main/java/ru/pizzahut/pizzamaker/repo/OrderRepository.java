package ru.pizzahut.pizzamaker.repo;

import org.springframework.data.repository.CrudRepository;
import ru.pizzahut.pizzamaker.model.order.Order;

public interface OrderRepository extends CrudRepository<Order, Integer> {
}

package ru.pizzahut.pizzamaker.repo;

import org.springframework.data.repository.CrudRepository;
import ru.pizzahut.pizzamaker.model.pizzaForOrder.PizzaForOrder;

public interface PizzasForOrderRepository extends CrudRepository<PizzaForOrder, Integer> {
}

package ru.pizzahut.pizzamaker.repo;

import org.springframework.data.repository.CrudRepository;
import ru.pizzahut.pizzamaker.model.PizzaForOrder;

public interface PizzasForOrderRepository extends CrudRepository<PizzaForOrder, Integer> {
}

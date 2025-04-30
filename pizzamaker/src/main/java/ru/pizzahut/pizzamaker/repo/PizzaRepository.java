package ru.pizzahut.pizzamaker.repo;

import org.springframework.data.repository.CrudRepository;
import ru.pizzahut.pizzamaker.model.Pizza;

public interface PizzaRepository extends CrudRepository<Pizza, Integer> {
}

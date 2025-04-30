package ru.pizzahut.pizzamaker.repo;

import org.springframework.data.repository.CrudRepository;
import ru.pizzahut.pizzamaker.model.PizzaBoard;

public interface PizzaBoardRepository extends CrudRepository<PizzaBoard, Integer> {
}

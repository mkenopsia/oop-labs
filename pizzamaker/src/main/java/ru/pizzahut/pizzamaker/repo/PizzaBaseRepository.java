package ru.pizzahut.pizzamaker.repo;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.repository.CrudRepository;
import ru.pizzahut.pizzamaker.model.PizzaBase;

public interface PizzaBaseRepository extends CrudRepository<PizzaBase, Integer> {
    PizzaBase findByType(String type);
}

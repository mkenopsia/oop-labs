package ru.pizzahut.pizzamaker.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.pizzahut.pizzamaker.model.Ingredient;

@Repository
public interface IngredientsRepository extends CrudRepository<Ingredient, Integer> {
}

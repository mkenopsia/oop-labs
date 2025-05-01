package ru.pizzahut.pizzamaker.service.api;

import ru.pizzahut.pizzamaker.controller.payload.IngredientPayload;
import ru.pizzahut.pizzamaker.model.Ingredient;

import java.util.NoSuchElementException;

public interface IngredientService {

    void save(IngredientPayload payload);

    Iterable<Ingredient> getIngredients();

    void update(Integer id, IngredientPayload payload);

    void delete(Integer id);

    Ingredient findByIngredientId(Integer id);
}

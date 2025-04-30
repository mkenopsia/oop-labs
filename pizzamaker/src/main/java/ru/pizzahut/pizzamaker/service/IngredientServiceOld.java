package ru.pizzahut.pizzamaker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pizzahut.pizzamaker.controller.payload.IngredientPayload;
import ru.pizzahut.pizzamaker.model.Ingredient;
import ru.pizzahut.pizzamaker.repo.IngredientsRepositoryOld;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class IngredientServiceOld {

    private final IngredientsRepositoryOld ingredientsRepositoryOld;

    public void save(IngredientPayload ingredientPayload) {
        this.ingredientsRepositoryOld.save(ingredientPayload);
    }

    public void update(Integer id, IngredientPayload ingredientPayload) {
        this.ingredientsRepositoryOld.update(id, ingredientPayload);
    }

    public void delete(Integer id) {
        this.ingredientsRepositoryOld.delete(id);
    }

    public Ingredient findIngredientByName(String name) {
        return this.ingredientsRepositoryOld.findIngredientByName(name)
                .orElseThrow(NoSuchElementException::new);
    }

    public List<Ingredient> getAllIngredients() {
        return this.ingredientsRepositoryOld.getAllIngredients();
    }

    public Ingredient findIngredientById(Integer id) {
        return this.ingredientsRepositoryOld.findIngredientById(id)
                .orElseThrow(NoSuchElementException::new);
    }
}

package ru.pizzahut.pizzamaker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pizzahut.pizzamaker.controller.payload.IngredientPayload;
import ru.pizzahut.pizzamaker.model.pizzaIngredients.Type.Ingredient;
import ru.pizzahut.pizzamaker.repo.IngredientsRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientsRepository ingredientsRepository;

    public void save(IngredientPayload ingredientPayload) {
        this.ingredientsRepository.save(ingredientPayload);
    }

    public void update(Integer id, IngredientPayload ingredientPayload) {
        this.ingredientsRepository.update(id, ingredientPayload);
    }

    public void delete(Integer id) {
        this.ingredientsRepository.delete(id);
    }

    public Ingredient findIngredientByName(String name) {
        return this.ingredientsRepository.findIngredientByName(name)
                .orElseThrow(NoSuchElementException::new);
    }

    public List<Ingredient> getAllIngredients() {
        return this.ingredientsRepository.getAllIngredients();
    }

    public Ingredient findIngredientById(Integer id) {
        return this.ingredientsRepository.findIngredientById(id)
                .orElseThrow(NoSuchElementException::new);
    }
}

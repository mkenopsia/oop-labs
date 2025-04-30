package ru.pizzahut.pizzamaker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pizzahut.pizzamaker.controller.payload.IngredientPayload;
import ru.pizzahut.pizzamaker.model.Ingredient;
import ru.pizzahut.pizzamaker.repo.IngredientsRepository;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientsRepository ingredientsRepository;

    public void save(IngredientPayload payload) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(payload.name());
        ingredient.setPrice(payload.price());
        ingredientsRepository.save(ingredient);
    }

    public Iterable<Ingredient> getIngredients() {
        return this.ingredientsRepository.findAll();
    }

    public void update(Integer id, IngredientPayload payload) {
        Ingredient ingredient = new Ingredient(id, payload.name(), payload.price());
        this.ingredientsRepository.save(ingredient);
    }

    public void delete(Integer id) {
        this.ingredientsRepository.deleteById(id);
    }

    public Ingredient findByIngredientId(Integer id) {
        return this.ingredientsRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }
}

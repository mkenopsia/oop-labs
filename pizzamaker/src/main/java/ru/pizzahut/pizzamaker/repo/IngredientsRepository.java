package ru.pizzahut.pizzamaker.repo;

import org.springframework.stereotype.Repository;
import ru.pizzahut.pizzamaker.controller.payload.IngredientPayload;
import ru.pizzahut.pizzamaker.model.pizzaIngredients.Type.Cheese;
import ru.pizzahut.pizzamaker.model.pizzaIngredients.Type.Ingredient;
import ru.pizzahut.pizzamaker.model.pizzaIngredients.Type.Meat;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class IngredientsRepository {

    private List<Ingredient> ingredients;
    private Integer id = 0;

    public IngredientsRepository() {
        this.ingredients = Arrays.stream(Cheese.values())
                .map(cheese -> new Ingredient(id++, cheese.getName(), cheese.getPrice())).collect(Collectors.toList());
        ingredients.addAll(Arrays.stream(Meat.values())
                .map(meat -> new Ingredient(id++, meat.getName(), meat.getPrice())).toList());

    }

    public void save(IngredientPayload ingredientPayload) {
        Ingredient ingredient = new Ingredient(id++, ingredientPayload.name(), ingredientPayload.price());
        this.ingredients.add(ingredient);
    }

    public void delete(Integer id) {
        this.ingredients.removeIf(i -> i.getId().equals(id));
    }

    public Optional<Ingredient> findIngredientByName(String name) {
        return ingredients.stream().filter(i -> i.getName().equals(name)).findFirst();
    }

    public Optional<Ingredient> findIngredientById(Integer id) {
        return ingredients.stream().filter(i -> i.getId().equals(id)).findFirst();
    }

    public List<Ingredient> getAllIngredients() {
        return ingredients;
    }

    public void update(Integer id, IngredientPayload ingredientPayload) {
        Ingredient ingredient = findIngredientById(id)
                .orElseThrow(NoSuchElementException::new);
        ingredient.setName(ingredientPayload.name());
        ingredient.setPrice(ingredientPayload.price());
    }
}

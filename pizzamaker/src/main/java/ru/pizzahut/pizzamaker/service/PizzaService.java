package ru.pizzahut.pizzamaker.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pizzahut.pizzamaker.controller.payload.PizzaPayload;
import ru.pizzahut.pizzamaker.model.Ingredient;
import ru.pizzahut.pizzamaker.model.Pizza;
import ru.pizzahut.pizzamaker.model.PizzaBase;
import ru.pizzahut.pizzamaker.repo.IngredientsRepository;
import ru.pizzahut.pizzamaker.repo.PizzaBaseRepository;
import ru.pizzahut.pizzamaker.repo.PizzaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class PizzaService {

    private final PizzaRepository pizzaRepository;
    private final IngredientsRepository ingredientsRepository;
    private final PizzaBaseRepository pizzaBaseRepository;


    public void save(PizzaPayload payload) {
        Pizza pizza = processPayload(payload);
        this.pizzaRepository.save(pizza);
    }

    private Pizza processPayload(PizzaPayload payload) {
        Pizza pizza = new Pizza();
        pizza.setName(payload.name());

        List<Ingredient> ingredients = new ArrayList<>();
        ingredientsRepository.findAll().forEach(ingredients::add);
        ingredients = ingredients.stream()
                .filter(pzz -> payload.ingredients().contains(pzz.getName()))
                .toList();

        if(ingredients.size() != payload.ingredients().size()) {
            throw new IllegalArgumentException("Одного из ингридиентов нет в базе");
        }

        pizza.setIngredients(ingredients);

        PizzaBase base = pizzaBaseRepository.findByType((payload.base()));
        pizza.setPizzaBase(base);

        pizza.setPrice(evalPrice(ingredients, base));

        return pizza;
    }

    private Double evalPrice(List<Ingredient> ingredients, PizzaBase base) {
        Double price = 0.0;
        price += (ingredients.stream().mapToDouble(Ingredient::getPrice).sum());
        price += base.getPrice();
        return price;
    }

    public Iterable<Pizza> getAllPizzas() {
        return this.pizzaRepository.findAll();
    }

    public void updatePizza(Integer id, PizzaPayload pizzaPayload) {
        Pizza pizza = processPayload(pizzaPayload);
        pizza.setId(id);
        this.pizzaRepository.save(pizza);
    }

    public Pizza getPizzaById(Integer id) {
        return this.pizzaRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }
}

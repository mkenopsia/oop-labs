package ru.pizzahut.pizzamaker.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pizzahut.pizzamaker.controller.payload.PizzaPayload;
import ru.pizzahut.pizzamaker.model.Ingredient;
import ru.pizzahut.pizzamaker.model.Pizza;
import ru.pizzahut.pizzamaker.model.PizzaBase;
import ru.pizzahut.pizzamaker.repo.IngredientsRepository;
import ru.pizzahut.pizzamaker.repo.PizzaBaseRepository;
import ru.pizzahut.pizzamaker.repo.PizzaRepository;
import ru.pizzahut.pizzamaker.service.api.PizzaService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class DefaultPizzaService implements PizzaService {

    private final PizzaRepository pizzaRepository;
    private final IngredientsRepository ingredientsRepository;
    private final PizzaBaseRepository pizzaBaseRepository;

    @Override
    public void save(PizzaPayload payload) {
        Pizza pizza = processPayload(payload);
        this.pizzaRepository.save(pizza);
    }

    @Override
    public Iterable<Pizza> getAllPizzas() {
        return this.pizzaRepository.findAll();
    }

    @Override
    public List<Pizza> getFilteredPizzas(String filter) {
        Iterable<Pizza> pizzas = this.pizzaRepository.findAll();
        List<Pizza> res = new ArrayList<>();
        for(var pizza : pizzas) {
            for(var ingredient : pizza.getIngredients()) {
                if(ingredient.getName().equalsIgnoreCase(filter)) {
                    res.add(pizza);
                }
            }
        }

        return res;
    }

    @Override
    public void updatePizza(Integer id, PizzaPayload pizzaPayload) {
        Pizza pizza = processPayload(pizzaPayload);
        pizza.setId(id);
        this.pizzaRepository.save(pizza);
    }

    @Override
    public Pizza getPizzaById(Integer id) {
        return this.pizzaRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
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
}

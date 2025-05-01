package ru.pizzahut.pizzamaker.service.api;

import ru.pizzahut.pizzamaker.controller.payload.PizzaPayload;
import ru.pizzahut.pizzamaker.model.Ingredient;
import ru.pizzahut.pizzamaker.model.Pizza;
import ru.pizzahut.pizzamaker.model.PizzaBase;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public interface PizzaService {

    void save(PizzaPayload payload);

    Iterable<Pizza> getAllPizzas();

    List<Pizza> getFilteredPizzas(String filter);

    void updatePizza(Integer id, PizzaPayload pizzaPayload);

    Pizza getPizzaById(Integer id);

}

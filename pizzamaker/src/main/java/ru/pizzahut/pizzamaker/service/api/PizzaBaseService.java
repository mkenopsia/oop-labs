package ru.pizzahut.pizzamaker.service.api;

import ru.pizzahut.pizzamaker.controller.payload.PizzaBasePayload;
import ru.pizzahut.pizzamaker.model.PizzaBase;

public interface PizzaBaseService {

    void save(PizzaBasePayload payload);

    Iterable<PizzaBase> getAllPizzaBases();

    void update(Integer id, PizzaBasePayload payload);

    void delete(Integer id);

    PizzaBase findPizzaBaseById(Integer id);
}

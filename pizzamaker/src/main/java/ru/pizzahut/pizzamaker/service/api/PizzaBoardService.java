package ru.pizzahut.pizzamaker.service.api;

import ru.pizzahut.pizzamaker.controller.payload.PizzaBoardPayload;
import ru.pizzahut.pizzamaker.model.PizzaBoard;

public interface PizzaBoardService {

    void save(PizzaBoardPayload payload);

    Iterable<PizzaBoard> getAllPizzaBoards();

    PizzaBoard getPizzaBoardById(Integer id);

    PizzaBoard getPizzaBoardBy(Integer id);

    void update(Integer id, PizzaBoardPayload payload);

    void delete(Integer id);
}

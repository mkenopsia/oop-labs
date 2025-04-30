package ru.pizzahut.pizzamaker.model.pizzaForOrder;

import ru.pizzahut.pizzamaker.model.PizzaBoard;
import ru.pizzahut.pizzamaker.model.PizzaSize;

public interface CommonPizzaForOrder {

    PizzaBoard getBoard();
    PizzaSize getPizzaSize();
}

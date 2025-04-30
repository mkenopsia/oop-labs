package ru.pizzahut.pizzamaker.model.pizzaIngredients;

import lombok.Getter;

@Getter
public enum Sauce{
    PIZZASAUCE("Соус 1000 островов", 20.9), BBQ("Соус барбекью", 22.2),
    CHEESESAUCE("Сырный соус", 20.7), CAESAR("Соус цезарь", 19.5),
    SPICY("Острый соус",28.3), TOMATOSAUCE("Томатный соус", 18.0);

    private final String name;
    private final Double price;

    Sauce(String name, Double price) {
        this.name = name;
        this.price = price;
    }
}

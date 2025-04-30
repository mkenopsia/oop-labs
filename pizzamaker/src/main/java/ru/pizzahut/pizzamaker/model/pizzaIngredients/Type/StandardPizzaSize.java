package ru.pizzahut.pizzamaker.model.pizzaIngredients.Type;

import lombok.Getter;

@Getter
public enum StandardPizzaSize {
    SMALL("25см", 0.9), MEDIUM("30см", 1.0), BIG("35см", 1.2);

    final String type;
    final Double coef;

    StandardPizzaSize(String type, Double coef) {
        this.type = type;
        this.coef = coef;
    }
}

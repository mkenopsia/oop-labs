package ru.pizzahut.pizzamaker.model.pizzaIngredients;

import lombok.Getter;

@Getter
public enum StandardPizzaBases {
    CLASSIC("Классическое тесто", 210.0), AMERICAN("Американское тесто", 233.5), ITALIAN("Итальянское тесто", 230.0),
    VEGETABLE("Овощная основа (кабачки+зелень)", 190.5), CHICKEN("Основа из куриного фарша", 250.4),
    POTATO("Картофельная основа", 220.0), LAVASH("Основа из лаваша", 200.0);

    private String type;
    private Double price;

    StandardPizzaBases(String type, Double price) {
        this.type = type;
        this.price = price;
    }
}

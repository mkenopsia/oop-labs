package ru.pizzahut.pizzamaker.model.pizzaIngredients;

import lombok.Getter;

@Getter
public enum Vegetable{
    TOMATOES("Помидоры", 110.9), MUSHROOMS("Грибы", 98.2),
    PEPPERS("Красные перцы", 115.7), CUCUMBERS("Огурцы", 93.5),
    POTATO("Картофель",108.3), BASIL("Базилик", 100.0);

    private final String name;
    private final Double price;

    Vegetable(String name, Double price) {
        this.name = name;
        this.price = price;
    }
}

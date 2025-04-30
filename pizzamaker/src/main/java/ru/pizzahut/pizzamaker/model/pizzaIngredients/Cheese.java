package ru.pizzahut.pizzamaker.model.pizzaIngredients;

import lombok.Getter;

@Getter
public enum Cheese{
    CHEDDAR("Чеддер", 270.9), GAUDA("Гауда", 320.2),
    MOZZARELLA("Моцарелла", 215.7), PARMESAN("Пармезан", 273.5),
    DORBLU("Дорблю",258.3);

    private final String name;
    private final Double price;

    Cheese(String name, Double price) {
        this.name = name;
        this.price = price;
    }
}

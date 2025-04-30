package ru.pizzahut.pizzamaker.model.pizzaIngredients;

import lombok.Getter;

@Getter
public enum Meat {
    HAM("Ветчина", 100.0), GRILLED_CHICKEN("Курочка гриль", 135.0), BAKED_BEEF("Запечёная говядина", 130.0),
    SMOKED_SAUSAGES("Копчёные колбаски", 125.0), BACON("Бекон", 110.0), PEPPERONI("Пепперони", 110.0);

    private final String name;
    private final Double price;

    Meat(String name, Double price) {
        this.name = name;
        this.price = price;
    }
}

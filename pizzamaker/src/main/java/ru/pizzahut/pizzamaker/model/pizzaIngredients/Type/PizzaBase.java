package ru.pizzahut.pizzamaker.model.pizzaIngredients.Type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PizzaBase {

    private Integer id;
    private String type;
    private Double price;
}

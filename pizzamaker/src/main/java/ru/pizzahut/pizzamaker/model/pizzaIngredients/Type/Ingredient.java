package ru.pizzahut.pizzamaker.model.pizzaIngredients.Type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {

    private Integer id;
    private String name;
    private Double price;

}
